package org.yimon.admin.util;


import org.yimon.admin.core.exception.ValidateException;
import org.yimon.admin.core.util.StringUtils;
import org.yimon.admin.util.constant.ResultCode;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @author: ym.gao
 * @description: DES加解密
 * @date: 2024/6/7 17:05
 */
public final class DesUtils {
    // 密钥 长度不得小于24
    private static final String ALGORITHM = "DES";
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

    private DesUtils() {
    }

    /**
     * 3DES加密
     *
     * @param plainText 需要加密数据
     * @return String
     */
    public static String encrypt(String plainText, String key) {
        if (StringUtils.isBlank(plainText)) {
            return StringUtils.EMPTY;
        }

        try {
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);//DES加密和解密过程中，密钥长度都必须是8的倍数
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv2 = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv2);
            // 执行加密操作
            byte[] encryptData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptData);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException |
                 IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new ValidateException(ResultCode.FAILURE.code(), "encrypt data is fail:" + e.getMessage());
        }
    }

    /**
     * 3DES解密
     *
     * @param encryptText 密文
     * @return String
     */
    public static String decrypt(String encryptText, String key) {
        if (StringUtils.isBlank(encryptText)) {
            return StringUtils.EMPTY;
        }

        try {
            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            // 正式执行解密操作
            byte[] decryptData = cipher.doFinal(Base64.getDecoder().decode(encryptText));
            return new String(decryptData, StandardCharsets.UTF_8);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException |
                 IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new ValidateException(ResultCode.FAILURE.code(), "decrypt data is fail:" + e.getMessage());
        }
    }
}
