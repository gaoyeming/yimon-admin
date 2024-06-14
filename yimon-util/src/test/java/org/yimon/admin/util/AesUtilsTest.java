package org.yimon.admin.util;

import org.junit.Test;
import org.yimon.admin.util.constant.GlobalConstants;

/**
 * @author: ym.gao
 * @description: AesUtils test
 * @date: 2024/6/7 19:09
 */
public class AesUtilsTest {

    @Test
    public void encrypt() {
        System.out.println(AesUtils.encrypt("13125106701", GlobalConstants.SECRET));
    }

    @Test
    public void decrypt() {
        System.out.println(AesUtils.decrypt(AesUtils.encrypt("111", GlobalConstants.SECRET), GlobalConstants.SECRET));
    }
}
