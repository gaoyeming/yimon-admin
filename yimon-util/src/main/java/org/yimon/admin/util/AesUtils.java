package org.yimon.admin.util;

import org.springframework.util.DigestUtils;
import org.yimon.admin.core.exception.ValidateException;
import org.yimon.admin.core.util.StringUtils;
import org.yimon.admin.util.constant.ResultCode;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author: ym.gao
 * @description: AES加解密
 * @date: 2024/6/7 17:05
 */
public final class AesUtils {

    private static final String ALGORITHM = "AES";

    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";


    private AesUtils() {
    }

    public static String encrypt(String plainText, String aesKey) {
        if (StringUtils.isBlank(plainText)) {
            return StringUtils.EMPTY;
        }

        try {
            byte[] digest = DigestUtils.md5Digest(aesKey.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(digest, ALGORITHM));
            byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new ValidateException(ResultCode.FAILURE.code(), "decrypt data is fail:" + e.getMessage());
        }
    }

    public static String decrypt(String encryptText, String aesKey) {
        if (StringUtils.isBlank(encryptText)) {
            return StringUtils.EMPTY;
        }

        try {
            byte[] digest = DigestUtils.md5Digest(aesKey.getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(digest, ALGORITHM));
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encryptText));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ValidateException(ResultCode.FAILURE.code(), "decrypt data is fail:" + e.getMessage());
        }
    }

}
