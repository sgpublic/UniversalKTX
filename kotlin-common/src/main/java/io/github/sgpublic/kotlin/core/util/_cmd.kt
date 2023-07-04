package io.github.sgpublic.kotlin.core.util

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader

/**
 * @author Madray Haven
 * @Date 2023/6/27 13:07
 */

fun sudo(vararg command: String): String {
    val exec = Runtime.getRuntime().exec("su")
    BufferedReader(InputStreamReader(exec.inputStream)).use { dis ->
        DataOutputStream(exec.outputStream).use { dos ->
            dos.writeBytes("${command.joinToString(" ")}\n")
            dos.flush()
            dos.writeBytes("exit\n")
            dos.flush()
        }
        val sb = StringBuilder()
        while (true) {
            sb.append(dis.readLine() ?: break)
        }
        exec.waitFor()
        return sb.toString()
    }
}

fun cmd(vararg command: String): String {
    val exec = Runtime.getRuntime().exec("${command.joinToString(" ")}\n")
    BufferedReader(InputStreamReader(exec.inputStream)).use { dis ->
        DataOutputStream(exec.outputStream).use { dos ->
            dos.writeBytes("exit\n")
            dos.flush()
        }
        val sb = StringBuilder()
        while (true) {
            sb.append(dis.readLine() ?: break)
        }
        exec.waitFor()
        return sb.toString()
    }
}