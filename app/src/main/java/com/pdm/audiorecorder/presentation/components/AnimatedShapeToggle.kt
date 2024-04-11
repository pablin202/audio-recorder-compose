package com.pdm.audiorecorder.presentation.components

import kotlin.math.sqrt
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AnimatedShapeToggle() {
    var isCircle by remember { mutableStateOf(true) }
    val animatable = remember { Animatable(50f) }
    val coroutineScope = rememberCoroutineScope()

    val rectangleSize = (animatable.value / sqrt(2f)).dp

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(animatable.value.dp)
            .clip(if (isCircle) CircleShape else RoundedCornerShape(10.dp)) // 10.dp para bordes redondeados del rectángulo
            .background(Color.Red)
            .border(5.dp, Color.White, if (isCircle) CircleShape else RoundedCornerShape(10.dp))
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    coroutineScope.launch {
                        if (isCircle) {
                            animatable.animateTo(
                                targetValue = 50f, // Tamaño objetivo para el círculo
                                animationSpec = tween(durationMillis = 300)
                            )
                        } else {
                            animatable.animateTo(
                                targetValue = 50f, // Vuelve al tamaño inicial para el círculo
                                animationSpec = tween(durationMillis = 300)
                            )
                        }
                        isCircle = !isCircle
                    }
                })
            }
    ) {
        // Condición para dibujar el rectángulo solo cuando no es un círculo
        if (!isCircle) {
            Box(
                modifier = Modifier
                    .size(rectangleSize)
                    .clip(RoundedCornerShape(10.dp)) // Bordes redondeados para el rectángulo
                    .background(Color.Blue)
            )
        }
    }
}


@Composable
fun AnimatedCircleToRoundedRectangle() {
    var isCircle by remember { mutableStateOf(true) }
    val targetCornerSize =
        if (isCircle) 50.dp else 10.dp // 50.dp para círculo, 10.dp para rectángulo con bordes redondeados
    val animatedCornerSize by animateDpAsState(targetValue = targetCornerSize, label = "")

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp) // Tamaño fijo del Box
            .background(Color.Red, RoundedCornerShape(animatedCornerSize))
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    isCircle = !isCircle
                })
            }
    ) {

    }
}

@Composable
fun AnimatedCircleToRoundedRectangleWithPadding(
    size: Dp,
    onClick: (startRecording: Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    val initialCornerSizePx = with(density) { 50.dp.toPx() }
    val initialPaddingPx = with(density) { 0.dp.toPx() }

    val cornerSizePx = remember { Animatable(initialCornerSizePx) }
    val paddingPx = remember { Animatable(initialPaddingPx) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    coroutineScope.launch {
                        if (cornerSizePx.value == initialCornerSizePx) {
                            onClick(true)
                            launch { paddingPx.animateTo(targetValue = with(density) { 15.dp.toPx() }) }
                            launch { cornerSizePx.animateTo(targetValue = with(density) { 10.dp.toPx() }) }
                        } else {
                            onClick(false)
                            launch { paddingPx.animateTo(targetValue = initialPaddingPx) }
                            launch { cornerSizePx.animateTo(targetValue = initialCornerSizePx) }
                        }
                    }
                })
            }
            .padding(with(density) { paddingPx.value.toDp() })
            .background(
                Color.Red,
                RoundedCornerShape(with(density) { cornerSizePx.value.toDp() })
            )
    ) {

    }
}


@Preview
@Composable
fun AnimatedShapeTogglePreview() {
    AnimatedCircleToRoundedRectangle()
}