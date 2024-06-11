package org.yimon.admin.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.yimon.admin.core.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MyCORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String origin = "*";
            String headerOrigin = request.getHeader("origin");
            if(!StringUtils.isBlank(headerOrigin) && (headerOrigin.contains("127.0.0.1") || headerOrigin.contains("localhost"))) {
                origin = headerOrigin;
            }
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            //语法post请求预检查跨域问题
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (Exception e) {
            log.error("MyCORSFilter error", e);
        }
    }

    @Override
    public void destroy() {
    }
}
