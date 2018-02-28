package com.zephyr.jobs.batch.listeners;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.Task;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.tongfei.progressbar.ProgressBar;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.zephyr.jobs.batch.BatchConfiguration.*;

@Slf4j
@Component
public class MockDataStepListener extends StepExecutionListenerSupport {

    private static final String BEGIN_MESSAGE = "Begin loading mock data. Will be imported:\n{}";
    private static final String END_MESSAGE = "End loading mock data. Status {}. Imported:\n{}";
    private static final String SCOPE_FORMAT = "%22s: %9d";

    @Setter(onMethod = @__(@Autowired))
    private ProgressBar progressBar;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        long tasksCount = stepExecution.getJobParameters().getLong(TASK_COUNT_JOB_PARAM);
        log.info(BEGIN_MESSAGE, getImportScope(tasksCount, stepExecution));
        progressBar.start();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        long tasksCount = stepExecution.getExecutionContext().getInt(CURRENT_INDEX);
        progressBar.stop();
        log.info(END_MESSAGE, stepExecution.getExitStatus(), getImportScope(tasksCount, stepExecution));
        return stepExecution.getExitStatus();
    }

    private String getImportScope(long tasksCount, StepExecution stepExecution) {
        long providersCount = getJobParam(stepExecution, PROVIDERS_PER_TASK_JOB_PARAM);
        long searchCriteriaCount = tasksCount * getJobParam(stepExecution, CRITERIA_PER_TASK_JOB_PARAM);
        long requestCount = searchCriteriaCount * providersCount * getJobParam(stepExecution, REQUEST_PER_CRITERIA_JOB_PARAM);
        long ratingCount = requestCount * getJobParam(stepExecution, RATING_PER_REQUEST_JOB_PARAM);

        return String.format(SCOPE_FORMAT, Task.class.getSimpleName(), tasksCount)
                + String.format(SCOPE_FORMAT, SearchCriteria.class.getSimpleName(), searchCriteriaCount)
                + String.format(SCOPE_FORMAT, MeteredSearchCriteria.class.getSimpleName(), searchCriteriaCount)
                + String.format(SCOPE_FORMAT, Request.class.getSimpleName(), requestCount)
                + String.format(SCOPE_FORMAT, Rating.class.getSimpleName(), ratingCount);
    }

    private long getJobParam(StepExecution stepExecution, String key) {
        return stepExecution.getJobParameters().getLong(key);
    }
}
