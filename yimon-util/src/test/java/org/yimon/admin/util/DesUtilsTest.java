package org.yimon.admin.util;

import org.junit.Test;
import org.yimon.admin.util.constant.GlobalConstants;

/**
 * @author: ym.gao
 * @description: DesUtils test
 * @date: 2024/6/7 19:09
 */
public class DesUtilsTest {

    @Test
    public void encrypt() {
        System.out.println(DesUtils.encrypt("111", GlobalConstants.SECRET));
    }

    @Test
    public void decrypt() {
        System.out.println(DesUtils.decrypt(DesUtils.encrypt("111", GlobalConstants.SECRET), GlobalConstants.SECRET));
    }
}
