package com.zephyr.data.protocol.dto;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.validation.DateRange;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@DateRange(from = "from", to = "to")
public class TaskDto {

    @URL
    @NotEmpty
    private String url;

    @NotEmpty
    private Set<SearchEngine> engines;

    private LocalDate from;
    private LocalDate to;

    @Valid
    @NotNull
    @Size(min = 1, max = 6)
    private List<SearchCriteriaDto> searchCriteria;
}
