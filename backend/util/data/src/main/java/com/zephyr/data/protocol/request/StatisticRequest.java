package com.zephyr.data.protocol.request;

import com.zephyr.validation.DateRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Wither;

import java.security.Principal;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@DateRange(from = "from", to = "to")
public class StatisticRequest {

    @NotNull
    @NonNull
    private String taskId;

    @Wither
    @NonNull
    private Principal principal;

    private LocalDate to;
    private LocalDate from;

    public String getUserId() {
        return principal.getName();
    }
}
