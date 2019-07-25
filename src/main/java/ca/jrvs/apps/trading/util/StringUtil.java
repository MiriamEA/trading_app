package ca.jrvs.apps.trading.util;

import java.util.List;

public class StringUtil {

    public static boolean isEmpty(List<String> strings) {
        return strings.stream().map(s -> s == null || s.equals("")).reduce(false, (a, b) -> a || b);
    }
}
