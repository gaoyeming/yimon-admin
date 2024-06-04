package org.yimon.admin.dal;

import com.ctrip.platform.dal.dao.DalClientFactory;
import org.yimon.admin.dal.entity.SysOperationLog;
import org.yimon.admin.dal.repository.CrudRepository;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: ym.gao
 * @description: TODO
 * @date: 2024/4/29 17:34
 */
public class CrudRepositoryTest {


    private static CrudRepository crudRepository;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /**
         * Initialize DalClientFactory.
         * The Dal.config can be specified from class-path or local file path.
         * One of follow three need to be enabled.
         **/
        DalClientFactory.initClientFactory(); // load from class-path Dal.config
        DalClientFactory.warmUpConnections();
        crudRepository = new CrudRepository();
    }


    @Test
    public void deleteTest() throws SQLException {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("id", 1);
        System.out.println(crudRepository.operate("MySql_yimon","DELETE FROM sys_operation_log WHERE id = ?", params));
    }

    @Test
    public void countTest() throws SQLException {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("id", 2);
        System.out.println(crudRepository.selectFirst("MySql_yimon","SELECT count(*) FROM sys_operation_log WHERE id = ? ", params, Integer.class));
    }

    @Test
    public void findListTest() throws SQLException {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//        params.put("id", 1);
        System.out.println(crudRepository.selectList("MySql_yimon","SELECT * FROM sys_operation_log ", params, 1, 20, Map.class));
    }

    @Test
    public void findFirstTest() throws SQLException {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//        params.put("id", 1);
        System.out.println(crudRepository.selectFirst("MySql_yimon","SELECT * FROM sys_operation_log ", params, SysOperationLog.class));
    }


}
