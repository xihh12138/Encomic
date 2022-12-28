package com.xihh.encomic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.xihh.encomic.ui.main.MainRoute
import com.xihh.encomic.ui.theme.EncomicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EncomicTheme {
                MainRoute(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}