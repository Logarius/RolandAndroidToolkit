package net.oschina.git.roland.androidtoolkit.common;

/**
 * Created by Roland on 2017/5/9.
 */

public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.trim().equals("") || str.trim().equalsIgnoreCase("null");
    }
}
