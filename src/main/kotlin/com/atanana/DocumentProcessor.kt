package com.atanana

import org.apache.commons.io.FilenameUtils.getBaseName
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime

abstract class DocumentProcessor<T>(protected val file: File) {
    val QUESTION_REGEX = Regex("^вопрос\\s*\\d*.*", setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))
    val TOUR_REGEX = Regex("^тур\\s*\\d*.*", setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))

    val HIDDEN_PREFIXES = listOf(
            "зачёт:",
            "зачет:",
            "комментарий:",
            "коментарий:",
            "источник:",
            "источники:",
            "авторы:",
            "автор:"
    )

    fun process() {
        file.inputStream().use {
            val document = createDocument(it)
            hideInfo(document)

            val newFilename = newFilename()
            val newFile = File(newFilename)
            writeFile(document, newFile)
        }
    }

    protected fun shouldBePartialHidden(text: String): Boolean {
        return HIDDEN_PREFIXES.filter { prefix -> text.startsWith(prefix, true) }.any()
    }

    private fun newFilename() = "${getBaseName(file.name)}_${LocalDateTime.now()}.${file.extension}"

    abstract fun createDocument(inputStream: InputStream): T
    abstract fun hideInfo(document: T)
    abstract fun writeFile(document: T, file: File)
}