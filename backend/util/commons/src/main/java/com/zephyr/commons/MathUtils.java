package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import static java.lang.Math.abs;

@UtilityClass
public class MathUtils {

    public int roundUp(int num, int divisor) {
        int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
    }
}
