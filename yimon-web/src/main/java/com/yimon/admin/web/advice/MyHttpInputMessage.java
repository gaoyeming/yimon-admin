package com.yimon.admin.web.advice;

import com.yimon.admin.util.GsonHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yeming.gao
 * @Description: 请求消息序列化处理
 * @date 2020/7/29 10:02
 */
@Slf4j
public class MyHttpInputMessage implements HttpInputMessage {

    private final HttpHeaders headers;
    private final InputStream body;

    MyHttpInputMessage(HttpInputMessage inputMessage, String ipAddress) throws IOException {
        Map<String, String> requestMap = new HashMap<>(6);
        requestMap.put("data", convertToString(inputMessage.getBody()));

        this.headers = inputMessage.getHeaders();
        this.body = new ByteArrayInputStream(GsonHolder.toJson(requestMap).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public InputStream getBody() {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

   private String convertToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }

}
