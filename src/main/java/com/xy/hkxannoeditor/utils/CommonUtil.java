package com.xy.hkxannoeditor.utils;

import java.math.BigDecimal;

import static java.math.RoundingMode.DOWN;

public class CommonUtil {

    public static String addFrame(String fs) {
        BigDecimal decimal = new BigDecimal(fs);
        decimal = decimal.add(BigDecimal.valueOf(0.000001));
        return decimal.setScale(6, DOWN).toString();
    }

    public static String addFrame(String fs, double frame) {
        BigDecimal decimal = new BigDecimal(fs);
        decimal = decimal.add(BigDecimal.valueOf(frame));
        return decimal.setScale(6, DOWN).toString();
    }
}
