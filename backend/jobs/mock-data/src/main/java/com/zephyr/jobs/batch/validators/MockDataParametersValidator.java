package com.zephyr.jobs.batch.validators;

import com.zephyr.jobs.batch.BatchConfiguration;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MockDataParametersValidator implements JobParametersValidator {

    private static final String NULL_PARAM_ERROR_MESSAGE = "'%s' param can't be Null";
    private static final String PARAM_OUT_OF_BOUND_ERROR_MESSAGE = "'%s' param can't be. Current %d";

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        checkParam(parameters.getLong(BatchConfiguration.TASK_COUNT_JOB_PARAM), BatchConfiguration.TASK_COUNT_JOB_PARAM);
        checkParam(parameters.getLong(BatchConfiguration.CRITERIA_PER_TASK_JOB_PARAM), BatchConfiguration.CRITERIA_PER_TASK_JOB_PARAM);
        checkParam(parameters.getLong(BatchConfiguration.REQUEST_PER_CRITERIA_JOB_PARAM), BatchConfiguration.REQUEST_PER_CRITERIA_JOB_PARAM);
        checkParam(parameters.getLong(BatchConfiguration.RATING_PER_REQUEST_JOB_PARAM), BatchConfiguration.RATING_PER_REQUEST_JOB_PARAM);
    }

    private void checkParam(Long param, String paramName) throws JobParametersInvalidException {
        if (Objects.isNull(param)) {
            throw new JobParametersInvalidException(String.format(NULL_PARAM_ERROR_MESSAGE, paramName));
        }

        if (param < 1) {
            throw new JobParametersInvalidException(String.format(PARAM_OUT_OF_BOUND_ERROR_MESSAGE, paramName, param));
        }
    }
}
