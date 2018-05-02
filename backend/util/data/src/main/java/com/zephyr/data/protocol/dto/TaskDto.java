package com.zephyr.data.protocol.dto;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.validation.DateRange;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TaskDto {

    @URL
    @NotEmpty
    private String url;

    @NotEmpty
    private String name;

    @NotEmpty
    private Set<SearchEngine> engines;

    private String userId;
    private boolean isShared;

    @Valid
    @NotNull
    @Size(min = 1, max = 6)
    private List<SearchCriteriaDto> searchCriteria;
}
