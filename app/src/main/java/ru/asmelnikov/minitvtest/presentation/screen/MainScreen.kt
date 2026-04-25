package ru.asmelnikov.minitvtest.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.asmelnikov.minitvtest.R
import ru.asmelnikov.minitvtest.presentation.components.BarType
import ru.asmelnikov.minitvtest.presentation.components.MessageBarContent
import ru.asmelnikov.minitvtest.presentation.components.VideoPlayerView
import ru.asmelnikov.minitvtest.presentation.components.rememberAnimatedBarState
import ru.asmelnikov.minitvtest.presentation.viewmodel.PlayerEvent
import ru.asmelnikov.minitvtest.presentation.viewmodel.VideoViewModel

@Composable
fun MainScreen(
    viewModel: VideoViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val barState = rememberAnimatedBarState()

    LaunchedEffect(Unit) {
        viewModel.playerEvent.collect { event ->
            when (event) {
                is PlayerEvent.ErrorSnackBar -> {
                    barState.animatedMessageBar(
                        message = event.message,
                        type = BarType.ERROR
                    )
                }
            }
        }
    }

    MessageBarContent(barState = barState, duration = 3000L) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {


            VideoPlayerView(
                state = uiState,
                initializePlayer = viewModel::initializePlayer,
                onSavePosition = viewModel::saveCurrentPosition,
                releasePlayer = viewModel::releasePlayer,
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding(),
                color = Color.Black.copy(alpha = 0.7f),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.play_now),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = uiState.currentVideo?.videoIdentifier
                            ?: stringResource(R.string.loading),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${stringResource(R.string.video)} ${uiState.currentIndex + 1} из ${uiState.videoList.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }

}