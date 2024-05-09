package com.yimon.admin.core.util;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author: ym.gao
 * @description: 字符串工具类
 * @date: 2024/4/28 19:29
 */
public class StringUtils {

    public static final String SPACE = " ";

    public static final String EMPTY = "";


    private StringUtils() {
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static String joinWith(String delimiter, Object... array) {
        if (array == null) {
            throw new IllegalArgumentException("Object varargs must not be null");
        } else {
            return join(array, delimiter);
        }
    }

    @SafeVarargs
    public static <T> String join(final T... elements) {
        return join(elements, null);
    }

    public static String join(final Object[] array, final String delimiter) {
        if (array == null) {
            return null;
        }
        return join(array, delimiter, 0, array.length);
    }

    public static String join(final Object[] array, final String delimiter, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        if (endIndex - startIndex <= 0) {
            return EMPTY;
        }
        final StringJoiner joiner = new StringJoiner(toStringOrEmpty(delimiter));
        for (int i = startIndex; i < endIndex; i++) {
            joiner.add(toStringOrEmpty(array[i]));
        }
        return joiner.toString();
    }

    private static String toStringOrEmpty(final Object obj) {
        return Objects.toString(obj, EMPTY);
    }
}
