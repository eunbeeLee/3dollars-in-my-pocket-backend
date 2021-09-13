package com.depromeet.threedollar.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtils {

    public static double round(double value) {
        if (value == 0) {
            return 0;
        }
        return Math.round(value * 10) / 10.0;
    }

}
