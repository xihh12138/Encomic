package com.xihh.encomic.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.xihh.encomic.ui.theme.EncomicTheme

@Composable
fun FindRoute(
    modifier: Modifier
) {
    FindScreen(
        modifier = modifier
    )
}

@Composable
private fun FindScreen(
    modifier: Modifier
) {
    val bgColor = EncomicTheme.colors.background
    Canvas(
        modifier = modifier.fillMaxSize(),
        onDraw = {
            drawRect(
                color = bgColor
            )
        }
    )
}