package com.yimon.admin.util.constant;

import com.yimon.admin.core.result.ReturnCode;

public class ResultCode extends ReturnCode {

    public static final ReturnCode TABLE_NOT_FIND = new ReturnCode("FAILURE", 402, "表不存在或未接入，请确认");

    public static final ReturnCode REJECT = new ReturnCode("FAILURE", 403, "请求拒绝");


    public static final ReturnCode DB_FAIL = new ReturnCode("FAILURE", 501, "DB操作失败");


    public ResultCode(String status, int code, String msg) {
        super(status, code, msg);
    }
}
