package com.xihh.encomic.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun OtherRoute(
    modifier: Modifier
) {
    OtherScreen(
        modifier = modifier
    )
}

@Composable
private fun OtherScreen(
    modifier: Modifier
) {
    Canvas(
        modifier = modifier.fillMaxSize(),
        onDraw = {
            drawRect(
                color = Color(
                    red = Random.nextInt(256),
                    green = Random.nextInt(256),
                    blue = Random.nextInt(256)
                )
            )
        }
    )
}