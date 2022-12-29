package com.xihh.encomic.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.xihh.encomic.ui.theme.EncomicTheme
import kotlin.random.Random

@Composable
fun BookShelfRoute(
    modifier: Modifier
) {
    BookShelfScreen(
        modifier = modifier
    )
}

@Composable
private fun BookShelfScreen(
    modifier: Modifier
) {
    val bgColor = EncomicTheme.colors.surface
    Canvas(
        modifier = modifier.fillMaxSize(),
        onDraw = {
            drawRect(
                color = bgColor
            )
        }
    )
}