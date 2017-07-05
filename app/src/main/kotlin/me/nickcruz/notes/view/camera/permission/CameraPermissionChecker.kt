package me.nickcruz.notes.view.camera.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import io.reactivex.Completable

/**
 * Created by Nick Cruz on 7/4/17
 */
class CameraPermissionChecker(val activity: FragmentActivity) {

    companion object {
        private val REQUEST_CAMERA_PERMISSION = 1
    }

    var hasPermission: Boolean = false
        get() = ContextCompat.checkSelfPermission(activity as Context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    /**
     * Returns a Completable that is successful if the user has granted permission. If the user has
     * not granted permission, calls onError.
     */
    fun checkPermission(): Completable {
        if (hasPermission) {
            return Completable.complete()
        }
        activity.requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        return Completable.error(NoCameraPermission())
    }

    class NoCameraPermission : Throwable("Must grant permission.")
}