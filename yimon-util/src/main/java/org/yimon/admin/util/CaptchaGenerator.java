package org.yimon.admin.util;

import org.yimon.admin.core.exception.UserDefinedException;
import org.yimon.admin.core.result.ReturnCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author: ym.gao
 * @description: 验证码
 * @date: 2024/6/7 15:27
 */
public class CaptchaGenerator {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 80;
    private static final int FONT_SIZE = 40;
    private static final int LINE_COUNT = 10;
    private static final int CHAR_COUNT = 5;

    /**
     * 验证码生成
     * @return Map<String, String>
     */
    public static Map<String, String> generateCaptcha() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        g.setColor(Color.BLACK);
        for (int i = 0; i < LINE_COUNT; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < CHAR_COUNT; i++) {
            char randChar = (char) (random.nextInt(26) + 'A');
            captcha.append(randChar);
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawString(String.valueOf(randChar), 40 * i + 10, 50);
        }
        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Map<String, String> result = new HashMap<>();
        try {
            ImageIO.write(image, "PNG", baos);
            result.put("ImageBase64Data", Base64.getEncoder().encodeToString(baos.toByteArray()));
            result.put("CaptchaData", captcha.toString());
        } catch (IOException e) {
            throw new UserDefinedException(ReturnCode.EXCEPTION.code(), "generate captcha fail");
        }
        return result;
    }
}
