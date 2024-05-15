package com.yimon.admin.util.constant;

import com.yimon.admin.core.result.ReturnCode;

public class ResultCode extends ReturnCode {

    public static final ReturnCode SUCCEED_NON_DATA = new ReturnCode("SUCCEED", 201, "无返回类容");

    public static final ReturnCode EXCEPTION_NOT_MATCH = new ReturnCode("EXCEPTION", 999, "响应类型不匹配，联系开发确认");

    public static final ReturnCode PARAMS_ERROR = new ReturnCode("FAILURE", 401, "参数错误");

    public static final ReturnCode DB_FAIL = new ReturnCode("FAILURE", 501, "DB操作失败");


    public ResultCode(String status, int code, String msg) {
        super(status, code, msg);
    }
}
