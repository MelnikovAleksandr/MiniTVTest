package ru.asmelnikov.minitvtest.presentation.viewmodel

import androidx.media3.exoplayer.ExoPlayer
import ru.asmelnikov.minitvtest.domain.model.VideoItem

data class VideoUiState(
    val currentVideo: VideoItem? = null,
    val videoList: List<VideoItem> = emptyList(),
    val currentIndex: Int = 0,
    val currentPosition: Long = 0,
    val exoPlayer: ExoPlayer? = null
)

sealed class PlayerEvent {
    data class ErrorSnackBar(val message: String) : PlayerEvent()
}