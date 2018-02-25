package com.zephyr.jobs.batch.writer;

import com.zephyr.rating.domain.Rating;
import com.zephyr.task.domain.Task;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingWriter implements ItemWriter<Task> {

    private ItemWriter<Rating> delegate;

    @Override
    public void write(List<? extends Task> items) {

    }
}
