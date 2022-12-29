package com.xihh.encomic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple600,
    primaryVariant = Purple800,
    secondary = Teal600,
    secondaryVariant = Teal800,
    background = Teal700,
    surface = Purple700,
    error = Red700,
    onPrimary = onPurple700,
    onSecondary = onTeal600,
    onBackground = onTeal700,
    onSurface = onPurple700,
    onError = Color.White,
)

private val LightColorPalette = lightColors(
    primary = Purple300,
    primaryVariant = Purple500,
    secondary = Teal300,
    secondaryVariant = Teal500,
    background = Teal100,
    surface = Purple100,
    error = Red500,
    onPrimary = onPurple100,
    onSecondary = onTeal300,
    onBackground = onTeal100,
    onSurface = onPurple100,
    onError = Color.White,
)

object EncomicTheme{

    var theme by mutableStateOf(Theme.Normal)

    fun changeTheme(theme: Theme?=null){
        this.theme= theme
            ?: if (this.theme == Theme.Night) {
                Theme.Normal
            } else {
                Theme.Night
            }
    }

    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    enum class Theme {
        Normal, Night
    }
}

@Composable
fun EncomicTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}