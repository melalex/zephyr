package com.zephyr.task.services.dto;

import com.zephyr.task.domain.SearchCriteria;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TaskDto {

    @URL
    @NotEmpty
    private String url;

    @Valid
    @NotNull
    @Size(min = 1, max = 6)
    private List<SearchCriteria> searchCriteria;
}
