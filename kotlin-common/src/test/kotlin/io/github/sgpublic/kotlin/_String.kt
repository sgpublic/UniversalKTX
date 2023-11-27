package io.github.sgpublic.kotlin

import io.github.sgpublic.kotlin.core.util.toChineseNumber
import org.junit.Test

/**
 * @author Madray Haven
 * @Date 2023/11/24 17:56
 */
class StringTest {
    @Test
    fun toChineseNumberTest() {
        assert(4.toChineseNumber() == "四")
        assert(13.toChineseNumber() == "十三")
        assert(10.toChineseNumber() == "十")
        assert(25.toChineseNumber() == "二十五")
        assert(20.toChineseNumber() == "二十")
        assert(122.toChineseNumber() == "一百二十二")
    }
}