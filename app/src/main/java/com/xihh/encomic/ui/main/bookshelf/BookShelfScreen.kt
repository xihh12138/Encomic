package com.xihh.encomic.ui.main.bookshelf

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.xihh.base.data.StateData
import com.xihh.base.utils.LogUtil
import com.xihh.base.utils.toast
import com.xihh.encomic.R
import com.xihh.encomic.ui.theme.EncomicTheme
import com.xihh.encomic.ui.theme.RoundCorner8
import kotlinx.coroutines.launch


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
        modifier = modifier,
        viewModel = viewModel()
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun BookShelfScreen(
    modifier: Modifier,
    viewModel: BookShelfViewModel,

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
            when (tabItems[it].first) {
                R.string.tab_favorites -> {
                    FavoritesRoute(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                }
                R.string.tab_history -> {
                    HistoryRoute(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                }
                R.string.tab_download -> {
                    DownloadRoute(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookShelfGridItem(
    modifier: Modifier,
    bean: Pair<Int, String>,
    onClick: () -> Unit,
    onLongClick : () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundCorner8)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick,
                onLongClick = onLongClick,
            ),
    ) {
        Image(
            painter = painterResource(id = bean.first),
            contentDescription = bean.second,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Text(text = bean.second)
    }
}

@Composable
fun BookShelfGridPage(
    modifier: Modifier,
    booksState: StateData<List<Pair<Int, String>>?>,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {

    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        state = listState,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.background(EncomicTheme.colors.secondary)
    ) {
        LogUtil.d("xihh", "BookShelfGridPage: booksState=$booksState")
        if (booksState.data != null) {
            items(booksState.data!!) {
                BookShelfGridItem(
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
                    bean = it,
                    onClick = onClick,
                    onLongClick = onLongClick,
                )
            }
        }
    }
}