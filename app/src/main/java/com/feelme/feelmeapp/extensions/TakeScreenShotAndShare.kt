package com.feelme.feelmeapp.extensions

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import java.io.ByteArrayOutputStream

class TakeScreenShotAndShare(private val context: Context, private val view: View) {
    fun getScreenShot() {
        val intent = Intent(Intent.ACTION_SEND).setType("image/*")
        val returnedBitmat = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmat)
        val bgDrawable = view.background
        if(bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        val bytes = ByteArrayOutputStream()
        returnedBitmat.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val contextValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "screenshot")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contextValues)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
    }
}