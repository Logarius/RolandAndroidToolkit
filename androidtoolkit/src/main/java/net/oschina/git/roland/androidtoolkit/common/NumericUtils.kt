package net.oschina.git.roland.androidtoolkit.common

import java.math.BigDecimal

/**
 * Created by Roland on 2017/5/9.
 */

object NumericUtils {

    /**
     * 获取四舍五入后的Double值
     * @param value 需要四舍五入的值
     * *
     * @param scale 几位小数
     * *
     * @return 四舍五入后的值
     */
    fun getRoundDoubleValue(value: Double, scale: Int): Double {
        return BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }
}
