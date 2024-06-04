package org.yimon.admin.service;

import java.util.Map;

public interface RepositoryService {

    Map<String, Object> execute(String tableName, Map<String, Object> paramsMap);
}
