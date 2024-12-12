package com.dicoding.glucoscan.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

private const val MAXIMAL_SIZE = 1000000
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun createTimestamp(type: String?): String {
    var format = "yyyyMMdd_HHmmss"
    when (type) {
        "date" -> format = "dd"
        "dateSimpleName" -> format = "EEE"
        "month" -> format = "MM"
        "monthName" -> format = "MMMM"
        "year" -> format = "yyyy"
    }
    val date = Date()
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(date)
}

@RequiresApi(Build.VERSION_CODES.O)
fun get7DateBehind(): List<String> {
    val now = Instant.now()
    val zoneId = ZoneId.of("Asia/Jakarta")

    val dates = mutableListOf<String>()
    for (i in 0..6) {
        val localDateTime = now.minusSeconds(i * 86400L).atZone(zoneId).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = localDateTime.format(formatter)
        dates.add(formattedDate)
    }

    return dates
}

fun changeFormatTimestamp(inputTimestamp: String, format: String, type: String? = null): String {
    var pastFormat: String = "yyyy-MM-dd HH:mm:ss"
    when(type){
        "date" -> pastFormat = "dd"
        "dateSimpleName" -> pastFormat = "EEE"
        "month" -> pastFormat = "MM"
        "monthName" -> pastFormat = "MMMM"
        "year" -> pastFormat = "yyyy"
        "yearMonthDate" -> pastFormat = "yyyy-MM-dd"
    }
    val inputFormat = SimpleDateFormat(pastFormat, Locale.getDefault())
    val date = inputFormat.parse(inputTimestamp)
    val outputDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return outputDateFormat.format(date!!)
}

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun uriToFile(imageUri: Uri, context: Context): File{
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun File.reduceFileImage(): File{
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -=5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

    return file
}