package org.yimon.admin.dal;

import com.ctrip.platform.dal.dao.DalClient;
import com.ctrip.platform.dal.dao.DalClientFactory;
import com.ctrip.platform.dal.dao.DalHints;
import org.yimon.admin.dal.dao.SysOperationLogDao;
import org.yimon.admin.dal.entity.SysOperationLog;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: ym.gao
 * @description: TODO
 * @date: 2024/4/29 15:17
 */
public class SysOperationLogDaoTest {

    private static final String DATA_BASE = "MySql_yimon";
    //ShardColModShardStrategy;columns=CountryID;mod=2;tableColumns=CityID;tableMod=4;separator=_;shardedTables=person

    private static DalClient client = null;
    private static SysOperationLogDao dao = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /**
         * Initialize DalClientFactory.
         * The Dal.config can be specified from class-path or local file path.
         * One of follow three need to be enabled.
         **/
        DalClientFactory.initClientFactory(); // load from class-path Dal.config
        DalClientFactory.warmUpConnections();
        client = DalClientFactory.getClient(DATA_BASE);
        dao = new SysOperationLogDao();
    }

    @Test
    public void testInsert() throws SQLException {
        SysOperationLog log = new SysOperationLog();
        log.setOperaUser("dal-user");
        log.setOperaTitle("dal-insert");

        DalHints hints = DalHints.createIfAbsent(null);
        int count = dao.insert(hints, log);
        System.out.println("insert count:" + count);
    }

    @Test
    public void testQueryByPage() throws SQLException {
        DalHints hints = DalHints.createIfAbsent(null);
        List<SysOperationLog> list = dao.queryAllByPage(1, 2, hints);
        System.out.println(list);
    }

    @Test
    public void testQueryAll() throws SQLException {
        DalHints hints = DalHints.createIfAbsent(null);
        List<SysOperationLog> list = dao.queryAll(hints);
        System.out.println(list);
    }
}
