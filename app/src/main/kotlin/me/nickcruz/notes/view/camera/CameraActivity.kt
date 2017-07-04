package me.nickcruz.notes.view.camera

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.arch.lifecycle.LifecycleActivity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_camera.*
import me.nickcruz.notes.R
import me.nickcruz.notes.model.Note
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.Log
import durdinapps.rxcamera2.RxCameraManager
import durdinapps.rxcamera2.wrappers.RxOpenCameraEvent


/**
 * Created by Nick Cruz on 6/24/17
 */
class CameraActivity : LifecycleActivity() {

    companion object {
        val EXTRA_NOTE = "note"

        fun getStartIntent(context: Context, note: Note) =
            Intent(context, CameraActivity::class.java).apply { putExtra(EXTRA_NOTE, note) }

        private val REQUEST_CAMERA_PERMISSION = 1
    }

    private lateinit var rxCameraManager: RxCameraManager
    private lateinit var rearCameraId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setActionBar(toolbar)

        rxCameraManager = RxCameraManager(getSystemService(Context.CAMERA_SERVICE) as CameraManager)

        rearCameraId = getRearFacingCamera(rxCameraManager.cameraManager)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
            return
        }

        openCamera(rxCameraManager, rearCameraId)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            openCamera(rxCameraManager, rearCameraId)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun openCamera(cameraManager: RxCameraManager, cameraId: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            return
        }
        cameraManager.openCamera(cameraId, Handler())
                .subscribe({ openCameraEvent ->
                    when (openCameraEvent.eventType) {
                        RxOpenCameraEvent.EventType.OPENED -> Log.v("CameraTest", "opened")
                        RxOpenCameraEvent.EventType.DISCONNECTED -> Log.v("CameraTest", "disconnected")
                        RxOpenCameraEvent.EventType.CLOSED -> Log.v("CameraTest", "closed")
                        RxOpenCameraEvent.EventType.ERROR -> Log.v("CameraTest", "error")
                        else -> Log.v("CameraTest", "neither of the normal 4")
                    }
                }, {
                    Log.e("CameraTest", it.message)
                })
    }

    /**
     * Finds the rear-facing camera and returns the camera Id. For use with
     * CameraManager.openCamera(cameraId, handler).
     */
    private fun getRearFacingCamera(cameraManager: CameraManager): String {
        return cameraManager.cameraIdList
                .map { it.to(cameraManager.getCameraCharacteristics(it)) }
                .first { (_, characteristics) -> characteristics[CameraCharacteristics.LENS_FACING] == CameraMetadata.LENS_FACING_BACK }
                .first
    }

    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            ConfirmationDialog().show(fragmentManager, null)
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }
    }

    class ConfirmationDialog : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
            val parent = parentFragment
            return AlertDialog.Builder(activity)
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, { _, _ ->
                        requestPermissions(arrayOf(Manifest.permission.CAMERA),
                                REQUEST_CAMERA_PERMISSION)
                    })
                    .setNegativeButton(android.R.string.cancel,
                            { _, _ ->
                                val activity = parent.activity
                                activity?.finish()
                            })
                    .create()
        }
    }
}