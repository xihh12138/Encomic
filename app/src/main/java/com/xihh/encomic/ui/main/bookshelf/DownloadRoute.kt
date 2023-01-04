package com.xihh.encomic.ui.main.bookshelf

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun DownloadRoute(
    modifier: Modifier,
    viewModel: BookShelfViewModel
) {
    val downloadState by viewModel.downloadDataFlow.collectAsState()
    BookShelfGridPage(
        modifier = modifier,
        booksState = downloadState,
        onClick = {
            viewModel.notifyActionEvent(BookShelfViewModel.ACTION_DOWNLOAD_CLICK)
        },
        onLongClick = {
            viewModel.notifyActionEvent(BookShelfViewModel.ACTION_DOWNLOAD_LONG_CLICK)
        }
    )

    SideEffect {
        viewModel.fetchDownloadComic(1)
    }
}