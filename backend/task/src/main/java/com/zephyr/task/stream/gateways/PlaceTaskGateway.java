package com.zephyr.task.stream.gateways;

import com.zephyr.data.dto.TaskDto;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface PlaceTaskGateway {

    void send(TaskDto task);
}