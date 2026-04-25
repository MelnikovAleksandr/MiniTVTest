package ru.asmelnikov.minitvtest.presentation.viewmodel

import android.content.Context
import android.view.Surface
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.asmelnikov.minitvtest.R
import ru.asmelnikov.minitvtest.domain.model.Resource
import ru.asmelnikov.minitvtest.domain.model.VideoReport
import ru.asmelnikov.minitvtest.domain.repository.VideoRepository
import ru.asmelnikov.minitvtest.utils.StringResourceProvider
import java.time.LocalDateTime

class VideoViewModel(
    private val repository: VideoRepository,
    private val stringResourceProvider: StringResourceProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoUiState())
    val uiState: StateFlow<VideoUiState> = _uiState.asStateFlow()

    private val _playerEvent = MutableSharedFlow<PlayerEvent>()
    val playerEvent: SharedFlow<PlayerEvent> = _playerEvent.asSharedFlow()

    init {
        loadVideoList()
    }

    @OptIn(UnstableApi::class)
    fun initializePlayer(context: Context, surface: Surface) {
        viewModelScope.launch {
            val video = _uiState.value.currentVideo ?: return@launch
            val existingPlayer = _uiState.value.exoPlayer

            if (existingPlayer != null) {
                existingPlayer.setVideoSurface(surface)
                existingPlayer.setMediaItem(MediaItem.fromUri(video.uri))
                existingPlayer.prepare()
                existingPlayer.playWhenReady = true
                existingPlayer.seekTo(_uiState.value.currentPosition)
            } else {
                val exoPlayer = ExoPlayer.Builder(context).build().also {
                    val mediaItem = MediaItem.fromUri(video.uri)
                    it.setVideoSurface(surface)
                    it.setMediaItem(mediaItem)
                    it.prepare()
                    it.playWhenReady = true
                    it.seekTo(_uiState.value.currentPosition)
                    it.addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(playbackState: Int) {
                            when (playbackState) {
                                Player.STATE_READY -> {
                                    if (_uiState.value.currentPosition == 0L) {
                                        saveReport()
                                    }
                                }

                                Player.STATE_ENDED -> {
                                    onVideoCompleted()
                                }

                                else -> {}
                            }
                        }

                        override fun onPlayerError(e: PlaybackException) {
                            viewModelScope.launch {
                                _playerEvent.emit(
                                    PlayerEvent.ErrorSnackBar(
                                        "${
                                            stringResourceProvider.getString(
                                                R.string.error
                                            )
                                        } ${e.message}"
                                    )
                                )
                            }
                        }
                    })
                }
                _uiState.value = _uiState.value.copy(exoPlayer = exoPlayer)
            }
        }
    }

    fun saveCurrentPosition() {
        viewModelScope.launch {
            _uiState.value.exoPlayer?.let {
                _uiState.value = _uiState.value.copy(currentPosition = it.currentPosition)
            }
        }
    }

    fun releasePlayer() {
        viewModelScope.launch {
            val player = _uiState.value.exoPlayer
            player?.release()
            _uiState.value = _uiState.value.copy(exoPlayer = null)
        }
    }

    private fun loadVideoList() {
        viewModelScope.launch {
            when (val videos = repository.getMediaList()) {
                is Resource.Error -> {
                    _playerEvent.emit(
                        PlayerEvent.ErrorSnackBar(
                            "${
                                stringResourceProvider.getString(
                                    R.string.error
                                )
                            } ${videos.errors?.errorMessage ?: ""}"
                        )
                    )

                }

                is Resource.Success -> {
                    if (videos.data?.isNotEmpty() == true) {
                        _uiState.value = _uiState.value.copy(
                            videoList = videos.data,
                            currentVideo = videos.data[0],
                            currentIndex = 0
                        )
                    }
                }
            }
        }
    }

    private fun saveReport() {
        _uiState.value.currentVideo?.let { video ->
            viewModelScope.launch {
                val report = VideoReport(
                    idVideo = video.videoId,
                    videoName = video.videoIdentifier,
                    startTime = LocalDateTime.now()
                )
                when (val result = repository.insertReport(report)) {
                    is Resource.Error -> {
                        _playerEvent.emit(
                            PlayerEvent.ErrorSnackBar(
                                "${
                                    stringResourceProvider.getString(
                                        R.string.error
                                    )
                                } ${result.errors?.errorMessage ?: ""}"
                            )
                        )
                    }

                    is Resource.Success -> {}
                }
            }
        }
    }

    private fun onVideoCompleted() {
        val videos = _uiState.value.videoList
        if (videos.isEmpty()) return

        val nextIndex = (_uiState.value.currentIndex + 1) % videos.size
        val nextVideo = videos[nextIndex]

        _uiState.value = _uiState.value.copy(
            currentIndex = nextIndex,
            currentVideo = nextVideo,
            currentPosition = 0
        )
    }

}