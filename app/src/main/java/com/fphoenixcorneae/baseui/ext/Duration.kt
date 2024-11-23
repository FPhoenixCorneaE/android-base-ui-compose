package com.fphoenixcorneae.baseui.ext

import java.util.concurrent.TimeUnit
import kotlin.time.Duration

/**
 * 格式化时长
 * @param isFull 是否格式化为完整时长
 * @return %02d: 格式说明符，用于格式化整数。d 表示整数，02 表示如果数字少于两位，会在前面补零以达到两位数
 */
fun Duration?.format(isFull: Boolean = true): String {
    val wholeSeconds = this?.inWholeSeconds ?: 0
    val hours = wholeSeconds / TimeUnit.HOURS.toSeconds(1)
    val minutes = (wholeSeconds % TimeUnit.HOURS.toSeconds(1)) / TimeUnit.MINUTES.toSeconds(1)
    val seconds = wholeSeconds % TimeUnit.MINUTES.toSeconds(1)
    return when {
        hours > 0 || isFull -> "%02d:%02d:%02d".format(hours, minutes, seconds)
        else -> "%02d:%02d".format(minutes, seconds)
    }
}