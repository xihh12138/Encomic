package com.xihh.encomic.ui.main.bookshelf

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun HistoryRoute(
    modifier: Modifier,
    viewModel: BookShelfViewModel
) {
    val historyState by viewModel.historyDataFlow.collectAsState()
    BookShelfGridPage(
        modifier = modifier,
        booksState = historyState,
        onClick = {
            viewModel.notifyActionEvent(BookShelfViewModel.ACTION_HISTORY_CLICK)
        },
        onLongClick = {
            viewModel.notifyActionEvent(BookShelfViewModel.ACTION_HISTORY_LONG_CLICK)
        }
    )

    SideEffect {
        viewModel.fetchHistoryComic(1)
    }
}