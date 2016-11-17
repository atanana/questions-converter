package com.atanana

import java.io.File
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter

fun main(args: Array<String>) {
    try {
        if (args.isNotEmpty()) {
            val file = File(args.first())
            processFile(file)
        } else {
            val fileChooser = JFileChooser()
            fileChooser.fileFilter = FileNameExtensionFilter("Question Packs", "docx")
            val result = fileChooser.showDialog(null, "Select questions file")
            if (result == JFileChooser.APPROVE_OPTION) {
                processFile(fileChooser.selectedFile)
            } else {
                throw RuntimeException("Provide a file!")
            }
        }
    } catch(e: Exception) {
        if (args.isNotEmpty()) {
            println(e.message)
        } else {
            JOptionPane.showMessageDialog(null, e.message, "Error", JOptionPane.ERROR_MESSAGE)
        }
    }
}

private fun processFile(file: File) {
    if (file.exists()) {
        val processor = when (file.extension.toLowerCase()) {
            "docx" -> DocxProcessor(file)
            else -> null
        }

        if (processor != null) {
            processor.process()
        } else {
            throw RuntimeException("Unknown type of file!")
        }
    } else {
        throw RuntimeException("File not exists!")
    }
}