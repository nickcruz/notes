package me.nickcruz.notes.view.camera

import android.arch.lifecycle.LifecycleActivity
import android.content.Context
import android.content.Intent
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.view.TextureView
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.content_camera.*
import me.nickcruz.notes.R
import me.nickcruz.notes.model.Note
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.params.StreamConfigurationMap
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.util.Size
import android.media.ImageReader.OnImageAvailableListener
import android.os.Handler
import android.view.Surface


/**
 * Created by Nick Cruz on 6/24/17
 */
class CameraActivity : LifecycleActivity(), TextureView.SurfaceTextureListener {

    companion object {
        val EXTRA_NOTE = "note"

        fun getStartIntent(context: Context, note: Note) =
            Intent(context, CameraActivity::class.java).apply { putExtra(EXTRA_NOTE, note) }
    }

    var previewSurfaceTexture: SurfaceTexture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setActionBar(toolbar)


        // Step 1: Set a surface texture listener to know when the surface texture is available
        previewTextureView.surfaceTextureListener = this
    }

    // Step 1: Surface Texture Listener implementation
    override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {

    }

    override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
        return true
    }

    override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
        previewSurfaceTexture = surfaceTexture

        fetchCameraData()
    }
    // End Step 1

    // Step 2: Fetch camera data
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null
    private var cc: CameraCharacteristics? = null
    private var streamConfigs: StreamConfigurationMap? = null
    private var jpegSizes: Array<out Size>? = null

    private fun fetchCameraData() {
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager?.cameraIdList?.get(0)
        cc = cameraManager?.getCameraCharacteristics(cameraId)
        streamConfigs = cc?.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        jpegSizes = streamConfigs?.getOutputSizes(ImageFormat.JPEG)

        setUpSurfacesForCapturingImages()
    }
    // End Step 2

    // Step 3
    private fun setUpSurfacesForCapturingImages() {
        val width = jpegSizes?.get(0)?.width
        val height = jpegSizes?.get(0)?.height
        if (width != null && height != null) {
            val jpegImageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
            jpegImageReader.setOnImageAvailableListener({

            }, Handler {

            })

            jpegImageReader


        val previewSurface = Surface(previewSurfaceTexture)
        val jpegCaptureSurface = jpegImageReader.surface
        }

    }
    // End Step 3

}