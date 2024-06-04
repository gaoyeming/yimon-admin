package org.yimon.admin.web.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.yimon.admin.core.async.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * @author yeming.gao
 * @Description: 自定义bean配置
 * @date 2021/1/18 18:53
 */
@Configuration
@EnableAspectJAutoProxy // 启用基于 AspectJ 的自动代理
public class WebConfig {

//    @Bean
//    public Gson gson() {
//        return GsonHolder.getInstance();
//    }

    /**
     * 配置springboot默认使用gson进行序列化反序列化
     *
     * @param gson gson对象
     * @return GsonHttpMessageConverter
     */
    @Bean
    public GsonHttpMessageConverter customGsonHttpMessageConverter(Gson gson) {
        return new GsonHttpMessageConverter(gson);
    }


    @Bean(name = "yimonAbortThreadPool")
    public ThreadPoolExecutor yimonAbortThreadPool() {
        ThreadPoolExecutor threadExecutor = new ThreadPoolExecutor();
        threadExecutor.newAbortThreadPool("yimon");
        return threadExecutor;
    }

    @Bean(name = "yimonDiscardThreadPool")
    public ThreadPoolExecutor yimonDiscardThreadPool() {
        ThreadPoolExecutor threadExecutor = new ThreadPoolExecutor();
        threadExecutor.newDiscardThreadPool("yimon");
        return threadExecutor;
    }
}