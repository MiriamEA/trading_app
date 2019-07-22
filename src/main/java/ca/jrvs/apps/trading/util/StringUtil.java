package ca.jrvs.apps.trading.util;

public class StringUtil {

    public static boolean isEmpty(String s1, String s2, String s3){
        return isEmpty(s1) || isEmpty(s2) || isEmpty(s3);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

}
