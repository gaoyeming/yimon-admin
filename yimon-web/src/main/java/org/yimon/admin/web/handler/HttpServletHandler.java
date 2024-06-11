package org.yimon.admin.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.yimon.admin.core.util.StringUtils;
import org.yimon.admin.util.GsonHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yeming.gao
 * @Description: HttpServlet处理类
 * @date 2020/7/29 10:02
 */
@Slf4j
public class HttpServletHandler {

    private static final String UNKNOWN = "unknown";

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return UNKNOWN;
        }
        return token;
    }

    public static String getUser(HttpServletRequest request) {
        String user = request.getHeader("user");
        if (StringUtils.isBlank(user)) {
            return UNKNOWN;
        }
        return user;
    }

    public static String getDevice(HttpServletRequest request) {
        Map<String, String> deviceMap = new HashMap<>();
        deviceMap.put("deviceType", getDeviceType(request));
        deviceMap.put("deviceTerminalNo", UNKNOWN);

        Map<String, String> deviceAddressMap = new HashMap<>();
        deviceAddressMap.put("IP", getDeviceIp(request));

        deviceMap.put("deviceAddress", GsonHolder.toJson(deviceAddressMap));

        return GsonHolder.toJson(deviceMap);
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getDeviceType(HttpServletRequest request) {
        String deviceType = UNKNOWN;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            Pattern pattern = Pattern.compile("(?i)(android|iphone|ipad|ipod|webos|blackberry|iemobile|opera mini)");
            Matcher matcher = pattern.matcher(userAgent);

            if (matcher.find()) {
                deviceType = matcher.group(1);
            } else {
                deviceType = userAgent;
            }
        }
        return deviceType;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getDeviceIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //因某些特殊情况可能会在HTTP头部参数中出现2个同名的X-Real-IP或X-Forwarded-For
        // 如SpringMVC获取参数时会将同名参数的值合并成一条，通过逗号分割，请大家注意不同语言获取多个同名参数的特殊情况。
        if (!StringUtils.isBlank(ip) && ip.contains(",")) {
            String[] realIps = ip.split(",");
            for (String realIp : realIps) {
                if (!StringUtils.isBlank(realIp) && !UNKNOWN.equalsIgnoreCase(realIp)) {
                    ip = realIp;
                    break;
                }
            }
        }
        return StringUtils.isBlank(ip) ? UNKNOWN : ip;
    }

}
