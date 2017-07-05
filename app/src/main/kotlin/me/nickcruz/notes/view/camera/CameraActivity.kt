package me.nickcruz.notes.view.camera

import android.arch.lifecycle.LifecycleActivity
import android.content.Context
import android.content.Intent
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.TextureView
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.content_camera.*
import me.nickcruz.notes.R
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.view.attachToLifecycle
import me.nickcruz.notes.view.camera.permission.CameraPermissionChecker
import java.lang.Long.signum
import java.util.*


/**
 * Created by Nick Cruz on 6/24/17
 */
class CameraActivity : LifecycleActivity() {

    companion object {
        val EXTRA_NOTE = "note"

        fun getStartIntent(context: Context, note: Note) =
            Intent(context, CameraActivity::class.java).apply { putExtra(EXTRA_NOTE, note) }
    }

    private val cameraPermissionChecker = CameraPermissionChecker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setActionBar(toolbar)

        openCamera()
    }

    override fun onResume() {
        super.onResume()

        if (autoFitTextureView.isAvailable) {
            openCamera()
        } else {
            autoFitTextureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
                }

                override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
                }

                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
                    return false
                }

                override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
                    openCamera()
                }
            }
        }
    }

    private fun openCamera() {
        cameraPermissionChecker.checkPermission()
                .andThen(NotesCamera(this).open())
                .subscribe({}, { Log.e("CameraTest", it.message) })
                .attachToLifecycle(this)
    }
}