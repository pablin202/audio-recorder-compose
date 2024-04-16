package com.pdm.audiorecorder.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pdm.audiorecorder.R
import com.pdm.audiorecorder.domain.models.AudioFile
import com.pdm.audiorecorder.ui.theme.Grey
import com.pdm.audiorecorder.ui.theme.Red
import com.pdm.audiorecorder.util.Common.formatDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerBottomSheet(
    modifier: Modifier = Modifier,
    audioFile: AudioFile,
    isPlaying: Boolean,
    currentTime: String,
    progress: Float,
    playAudio: (Int) -> Unit,
    pauseAudio: () -> Unit,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        ModalSheetContent(
            modifier = modifier,
            audioFile = audioFile,
            isPlaying = isPlaying,
            currentTime = currentTime,
            progress = progress,
            { playAudio(it) },
            { pauseAudio() },
        )
    }
}

@Composable
fun ModalSheetContent(
    modifier: Modifier = Modifier,
    audioFile: AudioFile,
    isPlaying: Boolean,
    currentTime: String,
    progress: Float,
    playAudio: (Int) -> Unit,
    pauseAudio: () -> Unit,
) {
    Box(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.size(46.dp),
                    painter = painterResource(id = R.drawable.sound_image),
                    contentDescription = "",
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = audioFile.name.removeSuffix(".mp3"),
                        color = Grey,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .size(46.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Button(
                        onClick = {
                            if (isPlaying) pauseAudio() else playAudio(audioFile.id)
                        },
                        modifier = Modifier.size(46.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonColors(
                            containerColor = Red,
                            disabledContainerColor = Red.copy(alpha = 0.4f),
                            contentColor = Red,
                            disabledContentColor = Red.copy(alpha = 0.4f)
                        )
                    ) {
                        // Adding an Icon "Add" inside the Button
                        Icon(
                            if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                            contentDescription = "content description",
                            tint = Color.White
                        )
                    }

                }
            }

            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = progress,
                onValueChange = { },
                colors = SliderDefaults.colors(
                    thumbColor = Red,
                    activeTrackColor = Red,
                    inactiveTrackColor = Red.copy(alpha = 0.4f),
                ),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = currentTime)
                Text(text = formatDuration(audioFile.duration))
            }
        }
    }
}

@Preview
@Composable
fun AudioPlayerBottomSheetPreview() {
    ModalSheetContent(
        modifier = Modifier,
        audioFile = AudioFile(
            name = "recording 001",
            duration = 30000
        ),
        isPlaying = true,
        currentTime = "1:00",
        progress = 0F,
        {},
        {},
    )
}