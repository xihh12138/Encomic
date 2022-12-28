package com.xihh.encomic.utils

import android.widget.Toast
import androidx.annotation.StringRes
import com.xihh.encomic.appContext

fun toast(string: String) {
    Toast.makeText(appContext, string, Toast.LENGTH_SHORT).show()
}

fun string(@StringRes id: Int): String = appContext.getString(id)

fun string(@StringRes id: Int, vararg args: String): String = appContext.getString(id, args)