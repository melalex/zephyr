package com.zephyr.data.protocol.request;

import com.zephyr.validation.DateRange;
import lombok.Data;

import java.security.Principal;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

@Data
@DateRange(from = "from", to = "to")
public class StatisticRequest {

    @NotNull
    private String taskId;
    private Principal principal;

    private LocalDate to = LocalDate.now();
    private LocalDate from = to.minusYears(1);

    public String getUserId() {
        return principal.getName();
    }
}
