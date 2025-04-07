package io.github.sgpublic.uniktx.common

import java.io.File
import java.nio.charset.Charset

fun File.writeAndClose(content: String, charset: Charset = Charsets.UTF_8) {
    delete()
    parentFile?.mkdirs()
    createNewFile()
    writer(charset).also {
        it.write(content)
        it.closeQuietly()
    }
}

fun File.child(name: String) = File(this, name)

fun File.deleteAll(): Boolean {
    val files: Array<File> = listFiles() ?: return true
    for (listFile in files) {
        if (listFile.isDirectory) {
            return deleteAll()
        }
        if (!listFile.delete()) {
            return false
        }
    }
    return true
}