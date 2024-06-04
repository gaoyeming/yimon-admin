package org.yimon.admin.util.constant;

import org.yimon.admin.core.result.ReturnCode;

public class ResultCode extends ReturnCode {

    public static final ReturnCode SUCCEED_NON_DATA = new ReturnCode("SUCCEED", 201, "no body");

    public static final ReturnCode EXCEPTION_NOT_MATCH = new ReturnCode("EXCEPTION", 999, "response type does not match, contact development to confirm");

    public static final ReturnCode PARAMS_ERROR = new ReturnCode("FAILURE", 401, "params error");

    public static final ReturnCode DB_FAIL = new ReturnCode("FAILURE", 501, "db operate fail");


    public ResultCode(String status, int code, String msg) {
        super(status, code, msg);
    }
}
