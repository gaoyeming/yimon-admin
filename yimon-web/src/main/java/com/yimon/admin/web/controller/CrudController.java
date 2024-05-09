package com.yimon.admin.web.controller;

import com.yimon.admin.biz.CrudBiz;
import com.yimon.admin.util.GsonHolder;
import com.yimon.admin.web.controller.vo.RequestVO;
import com.yimon.admin.web.controller.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: ym.gao
 * @description: 通用的crud入口
 * @date: 2024/4/29 16:33
 */
@RestController
@RequestMapping("crud")
public class CrudController {

    @Resource
    private CrudBiz crud;

    @PostMapping(value = "{method}")
    public ResultVO crud(@PathVariable String method, @RequestBody RequestVO request) {
        return ResultVO.isSucceed(crud.execute(method, GsonHolder.getMapObj(request.getData())));
    }
}
