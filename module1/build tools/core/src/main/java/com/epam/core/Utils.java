package com.epam.core;

import com.epam.task1.StringUtils;
import java.util.Arrays;

public class Utils {

    public static void main(String[] args) {
    }

    public static boolean isAllPositiveNumbers(String... str) {
        int before = str.length;
        int after = (int) Arrays.stream(str).filter(StringUtils::isPositiveNumber).count();
        return (before != 0 && before == after);
    }
}
