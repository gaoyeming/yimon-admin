package org.yimon.admin.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.yimon.admin.core.constant.DatePattern;
import org.yimon.admin.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public final class GsonHolder {
    private static final Gson INSTANCE_DEFAULT;

    private static final Gson INSTANCE_NORM_DATE;

    static {
        INSTANCE_DEFAULT = new Gson();
        // 创建GsonBuilder实例
        GsonBuilder gsonBuilder = new GsonBuilder();
        // 设置日期格式
        gsonBuilder.setDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        // 创建Gson实例
        INSTANCE_NORM_DATE = gsonBuilder.create();
    }

    private GsonHolder() {
    }

    public static Gson getInstance() {
        return INSTANCE_DEFAULT;
    }

    public static Gson getInstanceNormDate() {
        return INSTANCE_NORM_DATE;
    }


    public static Map<String, Object> getMapObj(String json) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            if (!StringUtils.isBlank(json)) {
                resultMap = GsonHolder.getInstance().fromJson(json, new TypeToken<HashMap<String, Object>>() {
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

    public static Map<String, String> getMapStr(String json) {
        Map<String, String> resultMap = new HashMap<>();

        try {
            if (!StringUtils.isBlank(json)) {
                resultMap = GsonHolder.getInstance().fromJson(json, new TypeToken<HashMap<String, String>>() {
                }.getType());
            }
        } catch (Exception ignored) {
            resultMap = null;
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

    public static String toJsonNormDate(Object obj) {
        return GsonHolder.getInstanceNormDate().toJson(obj);
    }
}
