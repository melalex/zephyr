package com.zephyr.jobs.batch.validators;

import com.zephyr.jobs.batch.BatchConfiguration;
import com.zephyr.task.domain.SearchEngine;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MockDataParametersValidator implements JobParametersValidator {

    private static final String NULL_PARAM_ERROR_MESSAGE = "'%s' param can't be Null";
    private static final String PARAM_LESS_THEN_ERROR_MESSAGE = "'%s' param can't be < 1. Current %d";
    private static final String PARAM_MORE_THEN_ERROR_MESSAGE = "'%s' param can't be > %d. Current %d";

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        checkParam(parameters.getLong(BatchConfiguration.TASK_COUNT_JOB_PARAM),
                BatchConfiguration.TASK_COUNT_JOB_PARAM);
        checkParam(parameters.getLong(BatchConfiguration.CRITERIA_PER_TASK_JOB_PARAM),
                BatchConfiguration.CRITERIA_PER_TASK_JOB_PARAM);
        checkParam(parameters.getLong(BatchConfiguration.REQUEST_PER_CRITERIA_JOB_PARAM),
                BatchConfiguration.REQUEST_PER_CRITERIA_JOB_PARAM);
        checkParam(parameters.getLong(BatchConfiguration.RATING_PER_REQUEST_JOB_PARAM),
                BatchConfiguration.RATING_PER_REQUEST_JOB_PARAM);

        checkProvidersParam(parameters);
    }

    private void checkParam(Long param, String paramName) throws JobParametersInvalidException {
        if (Objects.isNull(param)) {
            throw new JobParametersInvalidException(String.format(NULL_PARAM_ERROR_MESSAGE, paramName));
        }

        if (param < 1) {
            throw new JobParametersInvalidException(String.format(PARAM_LESS_THEN_ERROR_MESSAGE, paramName, param));
        }
    }

    private void checkProvidersParam(JobParameters parameters) throws JobParametersInvalidException {
        String paramName = BatchConfiguration.PROVIDERS_PER_TASK_JOB_PARAM;

        long providersPerTask = parameters.getLong(paramName);
        long maxProvidersCount = SearchEngine.values().length;

        checkParam(providersPerTask, paramName);

        if (providersPerTask > maxProvidersCount) {
            throw new JobParametersInvalidException(
                    String.format(PARAM_MORE_THEN_ERROR_MESSAGE, paramName, maxProvidersCount, providersPerTask));
        }
    }
}
