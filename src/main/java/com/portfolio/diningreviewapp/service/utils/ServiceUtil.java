package com.portfolio.diningreviewapp.service.utils;

import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class ServiceUtil {

    public static String validateZipcode(String zipcode) {

        Pattern pattern = Pattern.compile("\\b\\d{5}\\b");
        Matcher matcher = pattern.matcher(zipcode);

        Pattern pattern1 = Pattern.compile("\\b\\d{5}-\\d{4}\\b");
        Matcher matcher1 = pattern1.matcher(zipcode);

        if (matcher.matches() || matcher1.matches()) {
            return zipcode;
        }

        return null;
    }
}
