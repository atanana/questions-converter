package com.atanana

import java.io.File

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val file = File(args.first())
        if (file.exists()) {
            val processor = when (file.extension.toLowerCase()) {
                "docx" -> DocxProcessor(file)
                else -> null
            }

            if (processor != null) {
                processor.process()
            } else {
                println("Unknown type of file!")
            }
        } else {
            println("File not exists!")
        }
    } else {
        println("Provide a file!")
    }
}