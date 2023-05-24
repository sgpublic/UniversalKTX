package io.github.sgpublic.kotlin.core.util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Madray Haven
 * @Date 2023/4/12 13:08
 */
class ManualableScheduler(
    private val per: Long,
    private var afterInsertAction: (() -> Unit)? = null,
    private var onAction: (() -> Unit)? = null,
) {
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun setOnAction(block: (() -> Unit)?): ManualableScheduler {
        onAction = block
        return this
    }
    fun setAfterInsertAction(block: (() -> Unit)?): ManualableScheduler {
        afterInsertAction = block
        return this
    }

    private var started by AtomicBoolean(false)
    private var running by AtomicBoolean(false)
    fun start() {
        started = true
        paused = false

        val block: () -> Unit = {
            executor.execute {
                Thread.sleep(per)
                if (running) {
                    return@execute
                }
                running = true
                onAction?.invoke()
                running = false
            }
        }
        executor.execute {
            if (!paused) {
                onAction?.invoke()
            }
            block.invoke()
        }
    }
    fun stop() {
        started = false
    }

    fun insertAction(delay: Long = 0) {
        pause()
        executor.execute {
            if (!running) {
                running = true
                onAction?.invoke()
                running = false
            }
            while (running) {
                Thread.sleep(10)
            }
            afterInsertAction?.invoke()
        }
        resume()
    }

    private var paused by AtomicBoolean(false)
    fun pause() {
        paused = true
    }
    fun resume() {
        paused = false
    }
}
