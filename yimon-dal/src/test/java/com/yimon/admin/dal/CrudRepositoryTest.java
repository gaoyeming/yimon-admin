package com.yimon.admin.dal;

import com.ctrip.platform.dal.dao.DalClientFactory;
import com.yimon.admin.dal.entity.SysOperationLog;
import com.yimon.admin.dal.repository.CrudRepository;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.LinkedHashMap;

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
        System.out.println(crudRepository.operate("DELETE FROM sys_operation_log WHERE id = ?", params));
    }

    @Test
    public void countTest() throws SQLException {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("id", 2);
        System.out.println(crudRepository.selectFirst("SELECT count(*) FROM sys_operation_log WHERE id = ? ", params, Integer.class));
    }

    @Test
    public void findListTest() throws SQLException {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//        params.put("id", 1);
        System.out.println(crudRepository.selectList("SELECT * FROM sys_operation_log ", params, 1, 20, SysOperationLog.class));
    }

    @Test
    public void findFirstTest() throws SQLException {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//        params.put("id", 1);
        System.out.println(crudRepository.selectFirst("SELECT * FROM sys_operation_log ", params, SysOperationLog.class));
    }


}
