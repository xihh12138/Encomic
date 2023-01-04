package com.xihh.encomic.ui.main.bookshelf

import androidx.annotation.IntDef
import androidx.lifecycle.viewModelScope
import com.xihh.base.android.BaseViewModel
import com.xihh.base.data.StateData
import com.xihh.base.utils.LogUtil
import com.xihh.base.utils.toast
import com.xihh.encomic.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookShelfViewModel : BaseViewModel() {

    init {
        LogUtil.d("xihh", "BookShelfViewModel: init")
    }

    private val testFavouriteItem = listOf(
        Pair(R.drawable.ic_favorites, "漫画A"),
        Pair(R.drawable.ic_favorites, "漫画B"),
        Pair(R.drawable.ic_favorites, "漫画C"),
        Pair(R.drawable.ic_favorites, "漫画D"),
    )

    private val testHistoryItem = listOf(
        Pair(R.drawable.ic_time, "漫画E"),
        Pair(R.drawable.ic_time, "漫画F"),
        Pair(R.drawable.ic_time, "漫画G"),
        Pair(R.drawable.ic_time, "漫画H"),
    )

    private val favoritesData = MutableStateFlow(StateData<List<Pair<Int, String>>?>(null))
    val favoritesDataFlow = favoritesData.asStateFlow()

    private val historyData = MutableStateFlow(StateData<List<Pair<Int, String>>?>(null))
    val historyDataFlow = historyData.asStateFlow()

    private val downloadData = MutableStateFlow(StateData<List<Pair<Int, String>>?>(null))
    val downloadDataFlow = downloadData.asStateFlow()

    fun fetchFavouritesComic(page: Int) {
        LogUtil.d("xihh", "BookShelfViewModel: fetchFavouritesComic")
        viewModelScope.launch {
            if (page == 1) {
                favoritesData.emit(StateData.success(testFavouriteItem))
            } else {
                favoritesData.emit(StateData.failed(null))
            }
        }
    }

    fun fetchHistoryComic(page: Int) {
        LogUtil.d("xihh", "BookShelfViewModel: fetchHistoryComic")
        viewModelScope.launch {
            if (page == 1) {
                historyData.emit(StateData.success(testHistoryItem))
            } else {
                historyData.emit(StateData.failed(null))
            }
        }
    }

    fun fetchDownloadComic(page: Int) {
        LogUtil.d("xihh", "BookShelfViewModel: fetchDownloadComic")
        viewModelScope.launch {
            if (page == 1) {
                downloadData.emit(StateData.success(testFavouriteItem))
            } else {
                downloadData.emit(StateData.failed(null))
            }
        }
    }

    fun notifyActionEvent(@Action action: Int) {
        toast("$action")
    }

    companion object {
        const val ACTION_FAVOURITE_CLICK = 1
        const val ACTION_FAVOURITE_LONG_CLICK = 2

        const val ACTION_HISTORY_CLICK = 11
        const val ACTION_HISTORY_LONG_CLICK = 12

        const val ACTION_DOWNLOAD_CLICK = 21
        const val ACTION_DOWNLOAD_LONG_CLICK = 22
    }

    @Target(AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [
        ACTION_FAVOURITE_CLICK,
        ACTION_FAVOURITE_LONG_CLICK,
        ACTION_HISTORY_CLICK,
        ACTION_HISTORY_LONG_CLICK,
        ACTION_DOWNLOAD_CLICK,
        ACTION_DOWNLOAD_LONG_CLICK,
    ])
    annotation class Action {
    }
}