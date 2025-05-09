package vn.online.shop.onlineshop.common.util;

public class StringUtils {
    public static boolean isNullOrEmpty(final String s) {
        return isNullOrEmpty(s, true);
    }

    public static boolean isNullOrEmpty(final String s, boolean trim) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        return trim && s.trim().isEmpty();
    }
}
