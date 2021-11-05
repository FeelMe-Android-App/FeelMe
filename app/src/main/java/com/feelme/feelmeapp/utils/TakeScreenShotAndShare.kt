package com.feelme.feelmeapp.utils

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class TakeScreenShotAndShare(private val context: Context, private val view: View, private val height: Int, private val width: Int): AppCompatActivity() {
    fun getScreenShotUri(): Uri? {
        val returnedBitmat = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmat)
        val bgDrawable = view.background
        if(bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        val bytes = ByteArrayOutputStream()
        returnedBitmat.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        var fos: OutputStream?
        val filename = "screenshot.jpg"
        var uri: Uri?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                uri = imageUri
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            if(verifyUserPermission()) {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, filename)
                uri = image.toUri()
                fos = FileOutputStream(image)
            } else {
                uri = null
                fos = null
                Snackbar.make(view, "Permissão não concedida.", Snackbar.LENGTH_LONG)
            }
        }

        fos?.use {
            returnedBitmat.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        return uri
    }

    fun verifyUserPermission(): Boolean {
        var permission = false

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                permission = isGranted
            }

        when { ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                permission =  true
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        return permission
    }
}