package org.yimon.admin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.yimon.admin.core.log.BaseLogger;
import org.yimon.admin.core.log.LogLevel;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author: ym.gao
 * @description: 启动类
 * @date: 2024/4/28 16:16
 */
@SpringBootApplication(scanBasePackages = {"org.yimon.admin.*"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);

        try {
            System.setProperty("java.awt.headless", "false");
            Desktop.getDesktop().browse(new URI("http://127.0.0.1:8080/monitor"));
            BaseLogger.logWithLevel(LogLevel.DEBUG, "web running");
        }  catch (IOException | URISyntaxException e) {
            BaseLogger.logWithLevel(LogLevel.DEBUG, "web exception");
        }
    }
}
