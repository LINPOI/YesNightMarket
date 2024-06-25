package com.example.yesnightmarket.tool.pictureTool

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

fun uriToFile(context: Context, uri: Uri): File? {
    return when {
        uri.scheme == "file" -> {
            // 文件 URI 直接转换
            File(uri.path ?: return null)
        }
        uri.scheme == "content" -> {
            // 内容 URI，通过 ContentResolver 处理
            getFileFromContentUri(context, uri)
        }
        else -> null
    }
}

private fun getFileFromContentUri(context: Context, uri: Uri): File? {
    val fileName = getFileName(context, uri) ?: return null
    val tempFile = File(context.cacheDir, fileName)
    tempFile.createNewFile()
    var outputStream: OutputStream? = null
    var inputStream: InputStream? = null

    try {
        outputStream = FileOutputStream(tempFile)
        inputStream = context.contentResolver.openInputStream(uri) ?: return null
        inputStream.copyTo(outputStream)
    } finally {
        inputStream?.close()
        outputStream?.close()
    }
    return tempFile
}

private fun getFileName(context: Context, uri: Uri): String? {
    var fileName: String? = null
    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            fileName = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
        }
    }
    return fileName
}
