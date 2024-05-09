package com.yimon.admin.core;

import com.yimon.admin.biz.CrudBiz;
import com.yimon.admin.web.WebApplication;
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
public class CrudTest {

    @Resource
    private CrudBiz crudBiz;

    @Test
    public void putTest() {
        Map<String, Map<String, Object>> requestMap = new HashMap<>();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("opera_user", "ut-user");
        valueMap.put("opera_title", "ut-title");
        requestMap.put("sys_operation_log", valueMap);

        System.out.println(crudBiz.execute("put", requestMap));

    }

    @Test
    public void postTest() {
        Map<String, Map<String, Object>> requestMap = new HashMap<>();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("opera_user", "ut-user1");
        valueMap.put("opera_title", "ut-title1");
        valueMap.put("@WHERE@", "id");
        requestMap.put("sys_operation_log", valueMap);

        System.out.println(crudBiz.execute("post", requestMap));

    }

    @Test
    public void deleteTest() {
        Map<String, Map<String, Object>> requestMap = new HashMap<>();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        requestMap.put("sys_operation_log", valueMap);

        System.out.println(crudBiz.execute("delete", requestMap));

    }

    @Test
    public void getTest() {
        Map<String, Map<String, Object>> requestMap = new HashMap<>();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("@ORDER@", "id+");
        requestMap.put("sys_operation_log", valueMap);

        System.out.println(crudBiz.execute("get", requestMap));

    }

    @Test
    public void getsInTest() {
        Map<String, Map<String, Object>> requestMap = new HashMap<>();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("opera_user", Arrays.asList("dal-user", "2"));
        requestMap.put("sys_operation_log", valueMap);

        System.out.println(crudBiz.execute("gets", requestMap));

    }

    @Test
    public void getsTest() {
        Map<String, Map<String, Object>> requestMap = new HashMap<>();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("opera_user", "dal-user");
        requestMap.put("sys_operation_log", valueMap);

        System.out.println(crudBiz.execute("gets", requestMap));

    }

    @Test
    public void getsPageTest() {
        Map<String, Map<String, Object>> requestMap = new HashMap<>();

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("opera_user", "dal-user");
        valueMap.put("@PAGE_NO@", 1);
        valueMap.put("@PAGE_SIZE@", 5);
        valueMap.put("@ORDER@", "id-");
        requestMap.put("sys_operation_log", valueMap);

        System.out.println(crudBiz.execute("gets", requestMap));

    }
}
