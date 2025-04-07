package io.github.sgpublic.uniktx.common

import io.github.sgpublic.uniktx.common.core.util.ManualableScheduler
import org.junit.Test

/**
 * @author Madray Haven
 * @Date 2023/5/26 14:25
 */
class Scheduler {
    @Test
    fun test() {
        ManualableScheduler(1_000) {
            println("test start")
            Thread.sleep(1_000)
            println("test finish")
        }.start()
        Thread.sleep(60_100)
    }
}

