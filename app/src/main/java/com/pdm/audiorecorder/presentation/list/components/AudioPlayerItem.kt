package com.pdm.audiorecorder.presentation.list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pdm.audiorecorder.R
import com.pdm.audiorecorder.domain.models.AudioFile
import com.pdm.audiorecorder.ui.theme.Grey
import com.pdm.audiorecorder.ui.theme.Red
import com.pdm.audiorecorder.util.Common.formatDuration
import java.util.Date

@Composable
fun AudioPlayerItem(
    audioFile: AudioFile,
    onFavoriteChanged: (Int) -> Unit,
    onAudioSelected: (Int) -> Unit,
) {

    val rotationAngle: Float by animateFloatAsState(
        targetValue = if (audioFile.isFavorite) 360f else 0f, label = "",
        animationSpec = tween(
            durationMillis = 600,
        )
    )

    Row(
        modifier = Modifier
            .clickable {
                onAudioSelected(audioFile.id)
            }
            .fillMaxWidth()
            .height(70.dp)
            .padding(12.dp)

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
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = formatDuration(audioFile.duration),
                color = Grey,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .size(46.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .graphicsLayer(rotationZ = rotationAngle)
                    .clickable {
                        onFavoriteChanged(audioFile.id)
                    },
                painter = if (audioFile.isFavorite)
                    painterResource(id = R.drawable.favorite_filled_icon)
                else painterResource(
                    id = R.drawable.favorite_icon
                ),
                contentDescription = "",
                tint = if (audioFile.isFavorite) Red else Grey
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAudioPlayerItem() {
    AudioPlayerItem(
        audioFile = AudioFile(
            1,
            "recording_010101",
            30000, "", Date(), Date(),
            false
        ), {},
        onAudioSelected = {

        }
    )
}
