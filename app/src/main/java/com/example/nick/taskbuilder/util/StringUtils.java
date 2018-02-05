package com.example.nick.taskbuilder.util;


public class StringUtils {
    public static final String EMPTY_STR = "";

    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        if (str != null) {
            return EMPTY_STR.equals(str);
        }

        return true;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        if (isNotEmpty(str)) {
            return EMPTY_STR.equals(str.trim());
        }
        return true;
    }

    public static boolean lettersLimit(int n, String str){

        return str.length()<n;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String capitalizeFirstLetter(String str) {
        if(StringUtils.isBlank(str)) {
            return str;
        }

        Character firstChar = str.charAt(0);
        Character firstCapitalChar = Character.toUpperCase(firstChar);

        String result = firstCapitalChar + str.substring(1);

        return result;
    }
}
