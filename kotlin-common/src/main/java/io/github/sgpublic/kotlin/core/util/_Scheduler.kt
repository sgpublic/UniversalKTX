package io.github.sgpublic.kotlin.core.util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author Madray Haven
 * @Date 2023/4/12 13:08
 */
class SyncScheduler(
    private val per: Long,
    private val action: () -> Unit,
) {
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun start() {
        if (!stopFlag) {
            return
        }
        stopFlag = false
        doAction()
    }
    private var stopFlag = false
    private fun doAction() {
        executor.execute {
            action.invoke()
            Thread.sleep(per)

            if (!stopFlag) {
                doAction()
            }
        }
    }

    fun stop() {
        stopFlag = true
    }
}
