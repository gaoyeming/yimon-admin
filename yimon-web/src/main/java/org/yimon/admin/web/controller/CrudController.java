package org.yimon.admin.web.controller;

import org.springframework.web.bind.annotation.*;
import org.yimon.admin.biz.CrudBiz;
import org.yimon.admin.util.GsonHolder;
import org.yimon.admin.web.annotation.APIRequestLog;
import org.yimon.admin.web.controller.vo.RequestVO;
import org.yimon.admin.web.controller.vo.ResultVO;

import javax.annotation.Resource;

/**
 * @author: ym.gao
 * @description: 通用的crud入口， 如果不需要按表划分接口，则可以去除url中{tableName}；将具体操作表放入参数中
 * @date: 2024/4/29 16:33
 */
@RestController
//@RequestMapping("crud")
@RequestMapping("crud/{tableName}")
public class CrudController {

    @Resource
    private CrudBiz crud;

    @PostMapping(value = "{method}")
    @APIRequestLog(value = "CRUD操作")
    public ResultVO crud(@PathVariable String method, @PathVariable String tableName, @RequestBody RequestVO request) {
        return ResultVO.isSucceed(crud.execute(method, tableName, GsonHolder.getMapObj(request.getData())));
    }
}
