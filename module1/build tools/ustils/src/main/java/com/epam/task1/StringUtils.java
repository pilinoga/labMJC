package com.epam.task1;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils {

    public static boolean isPositiveNumber(String str) {
        return NumberUtils.isParsable(str) && NumberUtils.createLong(str) > 0;
    }

}
