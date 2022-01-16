package com.epam.task1;

import com.epam.task1.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    void isPositiveNumberPositive(){
        String number = "123";
        boolean positiveNumber = StringUtils.isPositiveNumber(number);
        Assertions.assertTrue(positiveNumber);
    }

    @Test
    void isPositiveNumberNegative(){
        String number = "qwerty";
        boolean notNumber = StringUtils.isPositiveNumber(number);
        Assertions.assertFalse(notNumber);
    }
}
