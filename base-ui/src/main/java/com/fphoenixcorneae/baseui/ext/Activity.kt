package com.fphoenixcorneae.baseui.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.util.Rational
import androidx.media3.ui.PlayerView

/**
 * Enables PIP mode for the current activity.
 *
 * @param defaultPlayerView Current video player controller.
 *
 * 注意：需要在xml中设置activity属性：android:supportsPictureInPicture="true"
 * android:launchMode="singleTask"
 * android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode"
 */
@SuppressLint("ObsoleteSdkInt")
@Suppress("DEPRECATION")
internal fun Activity.enterPictureInPictureMode(defaultPlayerView: PlayerView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N &&
        packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
    ) {
        defaultPlayerView.useController = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val params = PictureInPictureParams.Builder()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val sourceRectHint = Rect()
                defaultPlayerView.getGlobalVisibleRect(sourceRectHint)
                params
                    .setTitle("Video Player")
                    .setAspectRatio(Rational(16, 9))
                    .setSeamlessResizeEnabled(true)
                    .setSourceRectHint(sourceRectHint)
                    .setAutoEnterEnabled(true)
            }
            enterPictureInPictureMode(params.build())
        } else {
            enterPictureInPictureMode()
        }
    }
}