package com.fphoenixcorneae.baseui.media.video

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.annotation.RawRes
import androidx.media3.common.MediaItem.DrmConfiguration
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.AssetDataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.FileDataSource

/**
 * Representation of a media item for [androidx.media3.exoplayer.ExoPlayer].
 *
 * @see RawResourceMediaItem
 * @see AssetFileMediaItem
 * @see StorageMediaItem
 * @see NetworkMediaItem
 */
sealed interface VideoPlayerMediaItem {
    val mimeType: String
    val mediaMetadata: MediaMetadata

    /**
     * A media item in the raw resource.
     * @param rawResourceId R.raw.xxxxx resource id
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     */
    data class RawResourceMediaItem(
        @RawRes val rawResourceId: Int,
        override val mimeType: String,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
    ) : VideoPlayerMediaItem

    /**
     * A media item in the assets folder.
     * @param assetPath asset media file path (e.g If there is a test.mp4 file in the assets folder, test.mp4 becomes the assetPath.)
     * @throws androidx.media3.datasource.AssetDataSource.AssetDataSourceException asset file is not exist or load failed.
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     */
    data class AssetFileMediaItem(
        val context: Context,
        val assetPath: String,
        override val mimeType: String,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
    ) : VideoPlayerMediaItem

    /**
     * A media item in the device internal / external storage.
     * @param storageUri storage file uri
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     * @throws androidx.media3.datasource.FileDataSource.FileDataSourceException
     */
    data class StorageMediaItem(
        val storageUri: Uri,
        override val mimeType: String,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
    ) : VideoPlayerMediaItem

    /**
     * A media item in the internet
     * @param url network video url'
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     * @param drmConfiguration Drm configuration for media. (Default is null)
     */
    data class NetworkMediaItem(
        val url: String,
        override val mimeType: String,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        val drmConfiguration: DrmConfiguration? = null,
    ) : VideoPlayerMediaItem

    @OptIn(UnstableApi::class)
    fun toUri() = when (this) {
        is RawResourceMediaItem -> {
            Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .path(rawResourceId.toString())
                .build()
        }

        is AssetFileMediaItem -> {
            runCatching {
                val dataSpec = DataSpec(Uri.parse("asset:///$assetPath"))
                val assetDataSource = AssetDataSource(context)
                assetDataSource.open(dataSpec)
                assetDataSource.uri
            }.onFailure {
                it.printStackTrace()
            }.getOrDefault(Uri.EMPTY)
        }

        is NetworkMediaItem -> {
            Uri.parse(url)
        }

        is StorageMediaItem -> {
            runCatching {
                val dataSpec = DataSpec(storageUri)
                val fileDataSource = FileDataSource()
                fileDataSource.open(dataSpec)
                fileDataSource.uri
            }.onFailure {
                it.printStackTrace()
            }.getOrDefault(Uri.EMPTY)
        }
    }
}
