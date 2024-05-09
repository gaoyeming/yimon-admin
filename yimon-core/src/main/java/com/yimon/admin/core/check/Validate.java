package com.yimon.admin.core.check;

import com.yimon.admin.core.exception.ValidateException;
import com.yimon.admin.core.result.ReturnCode;
import com.yimon.admin.core.util.StringUtils;

/**
 * @author: ym.gao
 * @description: 参数校验类
 * @date: 2023/11/13 20:09
 */
public class Validate {

    public static void isNotBank(CharSequence str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new ValidateException(ReturnCode.FAILURE.code(), message);
        }
    }

    public static void isNonNull(Object object, String message) {
        if (object == null) {
            throw new ValidateException(ReturnCode.FAILURE.code(), message);
        }
    }

    public static void throwIllegality(Boolean result, String message) {
        if (!result) {
            throw new ValidateException(ReturnCode.FAILURE.code(), message);
        }
    }

    public static void throwNotAvailable(String message) {
        throw new ValidateException(ReturnCode.FAILURE.code(), message);
    }

    public static void minLength(CharSequence str, String message, int minLength) {
        Validate.isNonNull(str, message);
        if (str.length() < minLength) {
            throw new ValidateException(ReturnCode.FAILURE.code(), message);
        }
    }

    public static void maxLength(CharSequence str, String message, int maxLength) {
        Validate.isNonNull(str, message);
        if (str.length() > maxLength) {
            throw new ValidateException(ReturnCode.FAILURE.code(), message);
        }
    }

    public static void equalLength(CharSequence str, String message, int length) {
        Validate.isNonNull(str, message);
        if (str.length() != length) {
            throw new ValidateException(ReturnCode.FAILURE.code(), message);
        }
    }
}
