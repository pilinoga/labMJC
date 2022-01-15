package com.epam.task1;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils {

    public static void main(String[] args) {

    }

    public static boolean isPositiveNumber(String str) {
        boolean result;
        if(NumberUtils.isParsable(str)) {
            Long fromString = NumberUtils.createLong(str);
            result = fromString > 0;
        }else{
            result = false;
        }
        return result;
    }

}
