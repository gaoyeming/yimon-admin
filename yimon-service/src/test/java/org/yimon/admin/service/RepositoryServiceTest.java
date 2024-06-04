package org.yimon.admin.service;

import com.ctrip.platform.dal.dao.DalClientFactory;
import org.yimon.admin.dal.repository.CrudRepository;
import org.yimon.admin.service.impl.DELETE_Repository;
import org.yimon.admin.service.impl.POST_Repository;
import org.yimon.admin.service.impl.PUT_Repository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ym.gao
 * @description: TODO
 * @date: 2024/5/6 17:00
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.net.ssl.*", "javax.management.*"})
public class RepositoryServiceTest {

    @Mock
    private CrudRepository crudRepository;

    @InjectMocks
    private RepositoryService put = new PUT_Repository();

    @InjectMocks
    private RepositoryService post = new POST_Repository();

    @InjectMocks
    private RepositoryService delete = new DELETE_Repository();


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /**
         * Initialize DalClientFactory.
         * The Dal.config can be specified from class-path or local file path.
         * One of follow three need to be enabled.
         **/
        DalClientFactory.initClientFactory(); // load from class-path Dal.config
        DalClientFactory.warmUpConnections();

    }

    @Test
    public void defaultTest() {
        ARepositoryService repositoryService = new DELETE_Repository();
        System.out.println(repositoryService.getEntityClass("sys_operation_log"));
    }

    @Test
    public void insertTest() throws SQLException {
        PowerMockito.when(crudRepository.operate(Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(1);
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put("id", 1);
        insertMap.put("operaUser", "service-user");
        insertMap.put("operaTitle", "service-title");
        System.out.println(put.execute("sys_operation_log", insertMap));
    }

    @Test
    public void postTest() throws SQLException {
        PowerMockito.when(crudRepository.operate(Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(1);
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("id", 1);
        postMap.put("operaUser", "service-user1");
        postMap.put("operaTitle", "service-title1");
        postMap.put("@WHERE@", "id");
        System.out.println(post.execute("sys_operation_log", postMap));
    }

    @Test
    public void deleteTest() throws SQLException {
        PowerMockito.when(crudRepository.operate(Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(1);
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("id", 1);
        System.out.println(delete.execute("sys_operation_log", deleteMap));
    }
}
