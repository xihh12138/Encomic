package com.xihh.encomic.ui.main.bookshelf

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun FavoritesRoute(
    modifier: Modifier,
    viewModel: BookShelfViewModel
) {
    val favoritesState by viewModel.favoritesDataFlow.collectAsState()
    BookShelfGridPage(
        modifier = modifier,
        booksState = favoritesState,
        onClick = {
            viewModel.notifyActionEvent(BookShelfViewModel.ACTION_FAVOURITE_CLICK)
        },
        onLongClick = {
            viewModel.notifyActionEvent(BookShelfViewModel.ACTION_FAVOURITE_LONG_CLICK)
        }
    )

    SideEffect {
        viewModel.fetchFavouritesComic(1)
    }
}