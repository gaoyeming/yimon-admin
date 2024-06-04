package org.yimon.admin.biz;

import org.yimon.admin.dal.entity.SysOperationLog;
import org.yimon.admin.dal.repository.CrudRepository;
import org.yimon.admin.service.RepositoryService;
import org.yimon.admin.util.SpringContextHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.yimon.admin.service.impl.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ym.gao
 * @description: crud单元测试
 * @date: 2024/5/8 14:17
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringContextHandler.class})
@PowerMockIgnore({"javax.net.ssl.*", "javax.management.*"})
public class CrudBizTest {

    @InjectMocks
    private CrudBiz crudBiz = new CrudBiz();

    @InjectMocks
    private RepositoryService delete = new DELETE_Repository();

    @InjectMocks
    private RepositoryService get = new GET_Repository();

    @InjectMocks
    private RepositoryService gets = new GETS_Repository();

    @InjectMocks
    private RepositoryService post = new POST_Repository();

    @InjectMocks
    private RepositoryService put = new PUT_Repository();

    @Mock
    private CrudRepository crudRepository;

    @Before
    public void init() throws SQLException {
        PowerMockito.mockStatic(SpringContextHandler.class);
        Mockito.when(SpringContextHandler.getBeanByName("delete", RepositoryService.class)).thenReturn(delete);
        Mockito.when(SpringContextHandler.getBeanByName("get", RepositoryService.class)).thenReturn(get);
        Mockito.when(SpringContextHandler.getBeanByName("gets", RepositoryService.class)).thenReturn(gets);
        Mockito.when(SpringContextHandler.getBeanByName("post", RepositoryService.class)).thenReturn(post);
        Mockito.when(SpringContextHandler.getBeanByName("put", RepositoryService.class)).thenReturn(put);



        PowerMockito.when(crudRepository.operate(Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(1);
        PowerMockito.when(crudRepository.selectFirst(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(new SysOperationLog());
        PowerMockito.when(crudRepository.selectList(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn(new ArrayList<>());
    }

    @Test
    public void putTest() {

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("operaUser", "ut-user");
        valueMap.put("operaTitle", "ut-title");

        System.out.println(crudBiz.execute("put", "sys_operation_log", valueMap));

    }

    @Test
    public void postTest() {

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("operaUser", "ut-user1");
        valueMap.put("operaTitle", "ut-title1");
        valueMap.put("@WHERE@", "id");

        System.out.println(crudBiz.execute("post", "sys_operation_log", valueMap));

    }

    @Test
    public void deleteTest() {

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        System.out.println(crudBiz.execute("delete", "sys_operation_log", valueMap));

    }

    @Test
    public void getTest() {

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 1);
        valueMap.put("@ORDER@", "id+");

        System.out.println(crudBiz.execute("get", "sys_operation_log", valueMap));

    }

    @Test
    public void getsInTest() throws SQLException {
        PowerMockito.when(crudRepository.selectFirst(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(1);
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("operaUser", Arrays.asList("dal-user", "2"));

        System.out.println(crudBiz.execute("gets", "sys_operation_log", valueMap));

    }

    @Test
    public void getsTest() throws SQLException {
        PowerMockito.when(crudRepository.selectFirst(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(1);
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("operaUser", "dal-user");

        System.out.println(crudBiz.execute("gets", "sys_operation_log", valueMap));

    }

    @Test
    public void getsPageTest() throws SQLException {
        PowerMockito.when(crudRepository.selectFirst(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(1);

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("operaUser", "dal-user");
        valueMap.put("@PAGE_NO@", 1);
        valueMap.put("@PAGE_SIZE@", 5);
        valueMap.put("@ORDER@", "id-");

        System.out.println(crudBiz.execute("gets", "sys_operation_log", valueMap));

    }
}
