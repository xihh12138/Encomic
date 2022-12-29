package com.xihh.encomic.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.xihh.encomic.R
import com.xihh.encomic.ui.theme.EncomicTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val tabItems = arrayOf(
    Pair(R.string.tab_find, R.drawable.ic_view),
    Pair(R.string.tab_bookshelf, R.drawable.ic_zone),
    Pair(R.string.tab_other, R.drawable.ic_other),
)

@Composable
fun MainRoute(
    modifier: Modifier
) {
    val viewModel = viewModel<MainViewModel>()
    MainScreen(
        modifier = modifier,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainScreen(
    modifier: Modifier,
    viewModel: MainViewModel
) {
    val pageState = rememberPagerState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        bottomBar = {
            TabRow(
                selectedTabIndex = pageState.currentPage,
            ) {
                tabItems.forEachIndexed { index, pair ->
                    Tab(
                        selected = pageState.currentPage == index,
                        onClick = { scope.launch { pageState.animateScrollToPage(index) } },
                        modifier = Modifier.background(EncomicTheme.colors.surface)
                    ) {
                        Icon(
                            painter = painterResource(id = pair.second),
                            contentDescription = stringResource(id = pair.first),
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Text(
                            text = stringResource(id = pair.first),
                            color = EncomicTheme.colors.onSurface
                        )
                    }
                }
            }
        },
    ) {
        HorizontalPager(
            count = tabItems.size,
            state = pageState,
            key = { tabItems[it].first },
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (tabItems[it].first) {
                R.string.tab_find -> {
                    FindRoute(modifier = Modifier.fillMaxSize())
                }
                R.string.tab_bookshelf -> {
                    BookShelfRoute(modifier = Modifier.fillMaxSize())
                }
                R.string.tab_other -> {
                    OtherRoute(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.toastEvent.collectLatest {
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }

    val dialogState by viewModel.dialogState.collectAsState()
    DialogEffect(
        dialogState
    )
}

@Composable
private fun DialogEffect(
    dialogState: Int,
) {

}