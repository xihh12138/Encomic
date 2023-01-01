package com.xihh.encomic.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.xihh.base.utils.toast
import com.xihh.encomic.R
import com.xihh.encomic.ui.theme.EncomicTheme
import kotlinx.coroutines.launch
import kotlin.random.Random


private val tabItems = arrayOf(
    Pair(R.string.tab_favorites, R.drawable.ic_favorites),
    Pair(R.string.tab_history, R.drawable.ic_time),
    Pair(R.string.tab_download, R.drawable.ic_download),
)

@Composable
fun BookShelfRoute(
    modifier: Modifier
) {
    BookShelfScreen(
        modifier = modifier
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun BookShelfScreen(
    modifier: Modifier
) {
    val pageState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                TabRow(
                    selectedTabIndex = pageState.currentPage,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 100.dp),
                    backgroundColor = EncomicTheme.colors.background
                ) {
                    tabItems.forEachIndexed { index, pair ->
                        Tab(
                            selected = pageState.currentPage == index,
                            onClick = { scope.launch { pageState.animateScrollToPage(index) } },
                        ) {
                            Icon(
                                painter = painterResource(id = pair.second),
                                contentDescription = stringResource(id = pair.first),
                                modifier = Modifier.padding(top = 5.dp)
                            )
                            Text(
                                text = stringResource(id = pair.first),
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(id = R.string.menu_edit),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            toast("编辑")
                        }
                        .padding(4.dp)
                )
            }
        }
    ) {
        HorizontalPager(
            count = tabItems.size,
            state = pageState,
            modifier = Modifier.padding(it)
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
    }
}