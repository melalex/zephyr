package com.zephyr.errors.domain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Reasons {
    public static final Reason NOT_FOUND = Reason.isA("notFound");
    public static final Reason NOT_SET = Reason.isA("notSet");
}
