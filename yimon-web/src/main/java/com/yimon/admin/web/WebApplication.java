package com.yimon.admin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: ym.gao
 * @description: TODO
 * @date: 2024/4/28 16:16
 */
@SpringBootApplication(scanBasePackages = {"com.yimon.admin.*"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
