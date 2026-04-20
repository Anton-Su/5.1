package com.example.a51.ui.theme

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DataTxtSource(private val context: Context) {
    suspend fun getRepos(): List<RepositoryItem> {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files
                ?.filter { it.extension == "txt" }
                ?.map { file ->
                    val text = file.readText()
                    val nameNoExt = file.nameWithoutExtension
                    val timestamp = nameNoExt.substringBefore("_").toLongOrNull() ?: 0L
                    val fileName = nameNoExt.substringAfter("_")
                    RepositoryItem(
                        fileName = fileName,
                        text = text,
                        timestamp = timestamp
                    )
                }
                ?.sortedByDescending { it.timestamp }
                ?: emptyList()
        }
    }

    suspend fun saveItem(title: String, text: String): RepositoryItem {
        return withContext(Dispatchers.IO) {
            val timestamp = System.currentTimeMillis()
            val safeTitle = if (title.isBlank()) "untitled" else title.replace(" ", "_")
            val fileName = "${timestamp}_${safeTitle}.txt"
            val file = File(context.filesDir, fileName)
            file.writeText(text)
            RepositoryItem(
                fileName = safeTitle,
                text = text,
                timestamp = timestamp
            )
        }
    }

    suspend fun getById(id: Long): RepositoryItem? {
        return withContext(Dispatchers.IO) {
            val dir = context.filesDir
            val file = dir.listFiles()?.firstOrNull { it.extension == "txt" && it.nameWithoutExtension.startsWith("${id}_") }
            file?.let {
                val text = it.readText()
                val nameNoExt = it.nameWithoutExtension
                val fileName = nameNoExt.substringAfter("_")
                RepositoryItem(fileName = fileName, text = text, timestamp = id)
            }
        }
    }

    suspend fun updateItem(id: Long, newTitle: String, newText: String): RepositoryItem? {
        return withContext(Dispatchers.IO) {
            val dir = context.filesDir
            val file = dir.listFiles()?.firstOrNull { it.extension == "txt" && it.nameWithoutExtension.startsWith("${id}_") }
            file?.let {
                // preserve timestamp in filename, update title if changed
                val safeTitle = if (newTitle.isBlank()) "untitled" else newTitle.replace(" ", "_")
                val newFileName = "${id}_${safeTitle}.txt"
                val newFile = File(dir, newFileName)
                it.writeText(newText)
                if (it.name != newFile.name) {
                    // rename
                    it.renameTo(newFile)
                }
                RepositoryItem(fileName = safeTitle, text = newText, timestamp = id)
            }
        }
    }

}