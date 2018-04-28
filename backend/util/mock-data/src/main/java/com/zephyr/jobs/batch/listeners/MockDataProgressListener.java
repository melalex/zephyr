package com.zephyr.jobs.batch.listeners;

import com.zephyr.jobs.batch.BatchConfiguration;
import lombok.Setter;
import me.tongfei.progressbar.ProgressBar;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class MockDataProgressListener extends ChunkListenerSupport {

    @Setter(onMethod = @__(@Autowired))
    private ProgressBar progressBar;

    @Override
    public void afterChunk(ChunkContext context) {
        int current = context.getStepContext()
                .getStepExecution()
                .getExecutionContext()
                .getInt(BatchConfiguration.CURRENT_INDEX);

        progressBar.stepTo(current);
    }
}
