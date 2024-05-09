package com.yimon.admin.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yimon.admin.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public final class GsonHolder {
    private static final Gson INSTANCE = new Gson();

    private GsonHolder() {
    }

    public static Gson getInstance() {
        return INSTANCE;
    }


    public static Map<String, Map<String, Object>> getMapObj(String json) {
        Map<String, Map<String, Object>> resultMap = new HashMap<>();

        try {
            if (!StringUtils.isBlank(json)) {
                resultMap = GsonHolder.getInstance().fromJson(json, new TypeToken<HashMap<String, Map<String, Object>>>() {
                }.getType());
            }
        } catch (Exception ignored) {
        } finally {
            if (resultMap == null) {
                resultMap = new HashMap<>();
            }
        }
        return resultMap;
    }

    public static String toJson(Object obj) {
        return GsonHolder.getInstance().toJson(obj);
    }
}
