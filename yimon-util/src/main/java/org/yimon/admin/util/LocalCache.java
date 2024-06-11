package org.yimon.admin.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.yimon.admin.util.constant.GlobalConstants;

import java.util.concurrent.TimeUnit;

/**
 * @author: ym.gao
 * @description: 本地缓存工具类
 * @date: 2024/6/6 17:24
 */
public class LocalCache {

    private static final Cache<String, String> cache;

    static {
        // 创建一个缓存实例，设置缓存大小为1000
        cache = CacheBuilder.newBuilder()
                .initialCapacity(10)//设置初始缓存大小
                .maximumSize(1000)//设置最大缓存大小
                .expireAfterAccess(GlobalConstants.EXPIRE_TIME, TimeUnit.MINUTES)//设置过期时间
                .build();
    }

    /**
     * 向缓存中添加一个键值对，并设置过期时间
     *
     * @param key   键
     * @param value 值
     */
    public static void put(String key, String value) {
        cache.put(key, value);
    }

    /**
     * 从缓存中获取一个键对应的值
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * 从缓存中删除一个键值对
     *
     * @param key 键
     */
    public static void remove(String key) {
        cache.invalidate(key);
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        cache.invalidateAll();
    }
}
