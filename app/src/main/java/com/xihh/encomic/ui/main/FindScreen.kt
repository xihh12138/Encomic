package com.xihh.encomic.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xihh.base.utils.string
import com.xihh.base.utils.toast
import com.xihh.encomic.R
import com.xihh.encomic.ui.theme.EncomicTheme
import com.xihh.encomic.ui.theme.RoundCorner8

@Composable
fun FindRoute(
    modifier: Modifier
) {
    FindScreen(
        modifier = modifier,
        onSearchComic = {
            toast("搜索$it")
        }
    )
}

@Composable
private fun FindScreen(
    modifier: Modifier,
    onSearchComic: (String) -> Unit,
) {
    var searchText by remember { mutableStateOf("") }
    Scaffold(
        modifier = modifier,
        topBar = {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                label = {
                    Text(text = string(R.string.hint_main_search))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(EncomicTheme.colors.primary)
                    .statusBarsPadding(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = stringResource(R.string.function_search),
                        modifier = Modifier
                            .clickable {
                                onSearchComic(searchText)
                            }
                            .padding(8.dp)
                    )
                }
            )
        }
    ) {
        val color = EncomicTheme.colors.background
        val focusManager = LocalFocusManager.current
        Canvas(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .clickable {
                    focusManager.clearFocus()
                },
            onDraw = {
                drawRect(
                    color = color
                )
            }
        )
    }
}