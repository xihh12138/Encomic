package com.xihh.encomic.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.xihh.encomic.R
import com.xihh.encomic.utils.toast
import kotlinx.coroutines.launch
import kotlin.random.Random

private val tabItems = arrayOf(
    Pair(R.string.tab_find, R.drawable.ic_view),
    Pair(R.string.tab_bookshelf, R.drawable.ic_zone),
    Pair(R.string.tab_other, R.drawable.ic_other),
)

@Composable
fun MainRoute(
    modifier: Modifier
) {
    MainScreen(
        modifier = modifier,
        onTabClick = {
            toast("tab$it")
        },
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainScreen(
    modifier: Modifier,
    onTabClick: (Int) -> Unit,
) {
    val pageState = rememberPagerState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        bottomBar = {
            TabRow(selectedTabIndex = pageState.currentPage) {
                tabItems.forEachIndexed { index, pair ->
                    Tab(
                        selected = pageState.currentPage == index,
                        onClick = { scope.launch { pageState.animateScrollToPage(index) } }) {
                        Icon(
                            painter = painterResource(id = pair.second),
                            contentDescription = stringResource(id = pair.first),
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Text(
                            text = stringResource(id = pair.first)
                        )
                    }
                }
            }
        },
    ) {
        HorizontalPager(
            count = 3,
            state = pageState,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
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