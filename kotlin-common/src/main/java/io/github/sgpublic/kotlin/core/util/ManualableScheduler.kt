package io.github.sgpublic.kotlin.core.util

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Madray Haven
 * @Date 2023/4/12 13:08
 */
class ManualableScheduler(
    private val delay: Long,
    private var onInserted: (() -> Unit)? = null,
    private var task: (() -> Unit)? = null,
) {
    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    private val isRunning = AtomicBoolean(false)
    private val isTaskExecuting = AtomicBoolean(false)
    private val needCallOnInserted = AtomicBoolean(false)

    private var future: ScheduledFuture<*>? = null

    fun start() {
        synchronized(isRunning) {
            if (isRunning.compareAndSet(false, true)) {
                scheduleNextTask(0)
            }
        }
    }

    fun stop() {
        synchronized(isTaskExecuting) {
            if (!isTaskExecuting.get()) {
                future?.cancel(true)
            }
        }
        synchronized(isRunning) {
            isRunning.set(false)
        }
    }

    fun insert() {
        synchronized(isTaskExecuting) {
            if (!isTaskExecuting.get()) {
                future?.cancel(true)
                scheduleNextTask(0)
            } else {
                synchronized(needCallOnInserted) {
                    needCallOnInserted.set(true)
                }
            }
        }
    }

    @Synchronized
    private fun scheduleNextTask(delay: Long = this.delay) {
        future = executor.schedule(::runTask, delay, TimeUnit.MILLISECONDS)
    }

    @Synchronized
    private fun runTask() {
        synchronized(isRunning) {
            if (!isRunning.get()) {
                return
            }
        }
        synchronized(isTaskExecuting) {
            isTaskExecuting.set(true)
        }
        task?.invoke()
        synchronized(isTaskExecuting) {
            isTaskExecuting.set(false)
        }
        synchronized(needCallOnInserted) {
            if (needCallOnInserted.compareAndSet(true, false)) {
                onInserted?.invoke()
            }
        }
        scheduleNextTask()
    }
}
