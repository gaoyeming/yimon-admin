package com.yimon.admin.core;

import com.yimon.admin.util.GsonHolder;
import com.yimon.admin.web.WebApplication;
import com.yimon.admin.web.controller.CrudController;
import com.yimon.admin.web.controller.vo.RequestVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ym.gao
 * @description: crud单元测试
 * @date: 2024/5/8 14:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class CrudControllerTest {

    @Resource
    private CrudController crudController;

    @Test
    public void putTest() {

        RequestVO request = new RequestVO();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("operator_type", "ut-type1");
        valueMap.put("opera_user", "ut-user1");
        valueMap.put("opera_title", "ut-title1");
        valueMap.put("opera_device", "ut-device1");
        valueMap.put("opera_url", "ut-url1");
        valueMap.put("opera_param", "ut-param1");
        valueMap.put("opera_result", "ut-result1");
        valueMap.put("status", 0);
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("put", "sys_operation_log", request));

        valueMap.put("id", 2);
        valueMap.put("operator_type", "ut-type2");
        valueMap.put("opera_user", "ut-user2");
        valueMap.put("opera_title", "ut-title2");
        valueMap.put("opera_device", "ut-device2");
        valueMap.put("opera_url", "ut-url2");
        valueMap.put("opera_param", "ut-param2");
        valueMap.put("opera_result", "ut-result2");
        valueMap.put("status", 0);
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("put", "sys_operation_log", request));

        valueMap.put("id", 3);
        valueMap.put("operator_type", "ut-type3");
        valueMap.put("opera_user", "ut-user3");
        valueMap.put("opera_title", "ut-title3");
        valueMap.put("opera_device", "ut-device3");
        valueMap.put("opera_url", "ut-url3");
        valueMap.put("opera_param", "ut-param3");
        valueMap.put("opera_result", "ut-result3");
        valueMap.put("status", 0);
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("put", "sys_operation_log", request));

    }

    @Test
    public void postTest() {
        RequestVO request = new RequestVO();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("operator_type", "ut-type");
        valueMap.put("opera_user", "ut-user");
        valueMap.put("opera_title", "ut-title");
        valueMap.put("opera_device", "ut-device");
        valueMap.put("opera_url", "ut-url");
        valueMap.put("opera_param", "ut-param");
        valueMap.put("opera_result", "ut-result");
        valueMap.put("status", 1);
        valueMap.put("@WHERE@", "id");
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("post", "sys_operation_log", request));

    }

    @Test
    public void getTest() {
        RequestVO request = new RequestVO();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("status", 0);
        valueMap.put("@ORDER@", "id+");
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("get", "sys_operation_log", request));

        valueMap.put("status", 0);
        valueMap.put("@ORDER@", "id-");
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("get", "sys_operation_log", request));

    }

    @Test
    public void getsTest() {
        RequestVO request = new RequestVO();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("opera_user", "ut-user");
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("gets", "sys_operation_log", request));

    }

    @Test
    public void getsInTest() {
        RequestVO request = new RequestVO();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("status", Arrays.asList(0, 1));
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("gets", "sys_operation_log", request));

    }

    @Test
    public void getsPageTest() {
        RequestVO request = new RequestVO();

        Map<String, Object> valueMap = new HashMap<>();
//        valueMap.put("opera_user", "dal-user");
        valueMap.put("@PAGE_NO@", 1);
        valueMap.put("@PAGE_SIZE@", 2);
        valueMap.put("@ORDER@", "id-");
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("gets", "sys_operation_log", request));

        valueMap.put("@PAGE_NO@", 2);
        valueMap.put("@PAGE_SIZE@", 2);
        valueMap.put("@ORDER@", "id-");
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("gets", "sys_operation_log", request));

    }

    @Test
    public void deleteTest() {
        RequestVO request = new RequestVO();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("status", Arrays.asList(0, 1));
        request.setData(GsonHolder.toJson(valueMap));

        System.out.println(crudController.crud("delete", "sys_operation_log", request));

    }

}
