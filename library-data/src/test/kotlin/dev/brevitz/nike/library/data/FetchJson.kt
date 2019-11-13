package dev.brevitz.nike.library.data

import java.io.InputStreamReader

fun <T> Class<T>.loadFromFile(fileName: String): String {
    val inputStream = classLoader?.getResourceAsStream(fileName)
    val buffer = CharArray(1024)
    val out = StringBuilder()
    val input = InputStreamReader(inputStream, "UTF-8")

    while (true) {
        val rsz = input.read(buffer, 0, buffer.size)
        if (rsz < 0) {
            break
        }
        out.append(buffer, 0, rsz)
    }

    return out.toString()
}
