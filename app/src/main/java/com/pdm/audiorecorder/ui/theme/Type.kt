package com.pdm.audiorecorder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppFont {
    val Lexend = FontFamily(
        androidx.compose.ui.text.font.Font(com.pdm.audiorecorder.R.font.lexend_regular),
        androidx.compose.ui.text.font.Font(
            com.pdm.audiorecorder.R.font.lexend_bold,
            weight = FontWeight.Bold
        ),
        androidx.compose.ui.text.font.Font(
            com.pdm.audiorecorder.R.font.lexend_light,
            weight = FontWeight.Light
        ),
        androidx.compose.ui.text.font.Font(
            com.pdm.audiorecorder.R.font.lexend_medium,
            weight = FontWeight.Medium
        ),
    )
}

// Set of Material typography styles to start with
private val defaultTypography = Typography()
val typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.Lexend),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.Lexend),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.Lexend),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.Lexend),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.Lexend),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.Lexend),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.Lexend),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.Lexend),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.Lexend),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.Lexend),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.Lexend),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.Lexend),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.Lexend),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.Lexend),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.Lexend)
)