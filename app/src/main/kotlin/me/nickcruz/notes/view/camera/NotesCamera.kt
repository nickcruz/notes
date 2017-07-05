package me.nickcruz.notes.view.camera

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.util.Log
import android.util.Size
import durdinapps.rxcamera2.RxCameraManager
import io.reactivex.Single
import java.lang.Long
import java.util.*

/**
 * Created by Nick Cruz on 7/4/17
 */
class NotesCamera(private val context: Context) {

    fun open(): Single<RxCameraManager> {
        val rxCameraManager = RxCameraManager(context.getSystemService(Context.CAMERA_SERVICE) as CameraManager)

        rxCameraManager.openCamera()

        // setUpCameraOutputs
        // configureTransform

        return Single.just(rxCameraManager)
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

    /**
     * Given `choices` of `Size`s supported by a camera, choose the smallest one that
     * is at least as large as the respective texture view size, and that is at most as large as the
     * respective max size, and whose aspect ratio matches with the specified value. If such size
     * doesn't exist, choose the largest one that is at most as large as the respective max size,
     * and whose aspect ratio matches with the specified value.

     * @param choices The list of sizes that the camera supports for the intended output
     *                class
     * @param textureViewWidth The width of the texture view relative to sensor coordinate
     * @param textureViewHeight The height of the texture view relative to sensor coordinate
     * @param maxWidth The maximum width that can be chosen
     * @param maxHeight The maximum height that can be chosen
     * @param aspectRatio The aspect ratio
     * @return The optimal `Size`, or an arbitrary one if none were big enough
     */
    private fun chooseOptimalSize(choices: Array<Size>, textureViewWidth: Int,
                                  textureViewHeight: Int, maxWidth: Int, maxHeight: Int, aspectRatio: Size): Size {

        // Collect the supported resolutions that are at least as big as the preview Surface
        val bigEnough = ArrayList<Size>()
        // Collect the supported resolutions that are smaller than the preview Surface
        val notBigEnough = ArrayList<Size>()
        val w = aspectRatio.width
        val h = aspectRatio.height
        choices
                .filter { it.width <= maxWidth && it.height <= maxHeight && it.height == it.width * h / w }
                .forEach {
                    if (it.width >= textureViewWidth && it.height >= textureViewHeight) {
                        bigEnough.add(it)
                    } else {
                        notBigEnough.add(it)
                    }
                }

        // Pick the smallest of those big enough. If there is no one big enough, pick the
        // largest of those not big enough.
        if (bigEnough.size > 0) {
            return bigEnough.minWith(CompareSizesByArea()) ?: Size(0, 0)
        } else if (notBigEnough.size > 0) {
            return notBigEnough.maxWith(CompareSizesByArea()) ?: Size(0, 0)
        } else {
            Log.e("CameraTest", "Couldn't find any suitable preview size")
            return choices[0]
        }
    }

    /**
     * Compares two `Size`s based on their areas.
     */
    private class CompareSizesByArea : Comparator<Size> {
        override fun compare(lhs: Size, rhs: Size): Int {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)
        }
    }

}