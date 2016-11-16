package com.atanana

import org.apache.commons.io.FilenameUtils.getBaseName
import java.io.File
import java.time.LocalDateTime

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val file = File(args.first())
        if (file.exists()) {
            processDocx(file)
        } else {
            println("File not exists!")
        }
    } else {
        println("Provide a file!")
    }
}

val QUESTION_REGEX = Regex("^вопрос\\s*\\d*.*", setOf(RegexOption.IGNORE_CASE))
val TOUR_REGEX = Regex("^тур\\s*\\d*.*", setOf(RegexOption.IGNORE_CASE))

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

fun shouldBePartialHidden(text: String): Boolean {
    return HIDDEN_PREFIXES.filter { prefix -> text.startsWith(prefix, true) }.any()
}

fun newFilename(file: File) = "${getBaseName(file.name)}_${LocalDateTime.now()}.${file.extension}"