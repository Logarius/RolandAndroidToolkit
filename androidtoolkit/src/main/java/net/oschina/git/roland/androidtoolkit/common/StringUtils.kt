package net.oschina.git.roland.androidtoolkit.common

/**
 * Created by Roland on 2017/5/9.
 */

object StringUtils {

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * *
     * @return 是否为空
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || str.isEmpty() || str.trim { it <= ' ' } == "" || str.trim { it <= ' ' }.equals("null", ignoreCase = true)
    }
}
