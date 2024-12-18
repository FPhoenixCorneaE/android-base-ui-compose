package com.fphoenixcorneae.baseui.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build

/**
 * The environment in which Compose is hosted may not be an activity unconditionally.
 * Gets the current activity that is open from various kinds of contexts such as Fragment, Dialog, etc.
 */
internal fun Context.findActivity(): Activity? {
    if (this is Activity) {
        return this
    }
    if (this is ContextWrapper) {
        return baseContext.findActivity()
    }
    return null
}

/**
 * Check that the current activity is in PIP mode.
 *
 * @return `true` if the activity is in pip mode. (PIP mode is not supported in the version below Android N, so `false` is returned unconditionally.)
 */
@SuppressLint("ObsoleteSdkInt")
internal fun Context.isInPictureInPictureMode(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        findActivity()?.isInPictureInPictureMode ?: false
    } else {
        false
    }
}
