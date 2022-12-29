package com.xihh.base.utils

import android.widget.Toast
import androidx.annotation.StringRes
import com.xihh.base.android.appContext

fun toast(string: String) {
    Toast.makeText(appContext, string, Toast.LENGTH_SHORT).show()
}

fun string(@StringRes id: Int): String = appContext.getString(id)

fun string(@StringRes id: Int, vararg args: String): String = appContext.getString(id, args)