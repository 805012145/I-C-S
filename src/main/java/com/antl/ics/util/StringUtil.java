package com.antl.ics.util;

public class StringUtil {
    public static String[] StringToArray(String str) {
        String str1 = str.replace('{',' ');
        String str2 = str1.replace('}', ' ');
        String str3 = str2.replace('[',' ');
        String str4 = str3.replace(']', ' ');
        String str5 = str4.replace('(', ' ');
        String str6 = str5.replace(')', ' ');
        String str7 = str6.replaceAll(" ", "");
        return str7.split(",");
    }

    public static boolean hasStr(String src, String dst, String route) {
        String str = src+","+dst;
        String str1 = route.replaceAll(" ", "");
        if (str1.contains(str)) {
            return true;
        }
        return false;
    }

}
