package com.pdm.audiorecorder.presentation.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pdm.audiorecorder.ui.theme.AppFont
import com.pdm.audiorecorder.ui.theme.Grey
import com.pdm.audiorecorder.util.Common.formatDuration

@Composable
fun AudioPlayerItem(
    audioName: String,
    audioDurationMs: Long,
    onAudioSelected: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable {
                onAudioSelected(audioName)
            }
            .fillMaxWidth()
            .height(70.dp)
            .padding(12.dp)

    ) {
        Image(
            modifier = Modifier.size(46.dp),
            painter = painterResource(id = com.pdm.audiorecorder.R.drawable.sound_image),
            contentDescription = "",
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = audioName.removeSuffix(".mp3"),
                color = Grey,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = formatDuration(audioDurationMs),
                color = Grey,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAudioPlayerItem() {
    AudioPlayerItem(
        audioName = "Ejemplo",
        audioDurationMs = 300000,
        onAudioSelected = {

        }
    )
}
