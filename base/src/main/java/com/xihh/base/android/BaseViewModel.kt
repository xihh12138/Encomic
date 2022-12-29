package com.xihh.base.android

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel : ViewModel() {

    protected val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    protected val _dialogState = MutableStateFlow(0)
    val dialogState = _dialogState.asStateFlow()

    fun updateDialogVisibility(dialogFlag: Int, isVisible: Boolean) {
        if (isVisible) {
            setDialogFlag(dialogFlag, dialogFlag)
        } else {
            setDialogFlag(0, dialogFlag)
        }
    }

    private fun setDialogFlag(dialogFlag: Int, dialogMask: Int) {
        _dialogState.update { (it and dialogMask.inv()) or (dialogFlag and dialogMask) }
    }

    companion object {
        const val DIALOG_FLAG_LOADING = 0x01
    }
}