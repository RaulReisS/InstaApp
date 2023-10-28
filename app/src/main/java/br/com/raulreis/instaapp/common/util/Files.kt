package br.com.raulreis.instaapp.common.util

import android.app.Activity
import br.com.raulreis.instaapp.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

object Files {

    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    fun generateFile(activity: Activity) : File {
        val mediaDir = activity.externalMediaDirs.firstOrNull()?.let {
            File(it, activity.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }

        val outputDir = if (mediaDir != null && mediaDir.exists())
            mediaDir else activity.filesDir

        return File(outputDir, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())+".jpg")
    }
}