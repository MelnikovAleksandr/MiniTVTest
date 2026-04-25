package ru.asmelnikov.minitvtest.presentation.components

import android.content.Context
import android.content.res.Configuration
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import ru.asmelnikov.minitvtest.presentation.viewmodel.VideoUiState

@Composable
fun VideoPlayerView(
    state: VideoUiState,
    initializePlayer: (Context, Surface) -> Unit,
    onSavePosition: () -> Unit,
    releasePlayer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSurface = remember { mutableStateOf<Surface?>(null) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current

    LaunchedEffect(state.currentVideo, currentSurface.value) {
        currentSurface.value?.let { surface ->
            initializePlayer(context, surface)
        }
    }

    AndroidView(
        factory = { ctx ->
            SurfaceView(ctx).apply {
                holder.addCallback(object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        currentSurface.value = holder.surface
                    }
                    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                        currentSurface.value = null
                        onSavePosition()
                        releasePlayer()
                    }
                })
            }
        },
        modifier = if (isLandscape) {
            modifier
                .fillMaxHeight()
                .aspectRatio(9f / 16f)
        } else {
            modifier.fillMaxSize()
        }
    )
}