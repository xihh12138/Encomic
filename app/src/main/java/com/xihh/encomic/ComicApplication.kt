package com.xihh.encomic

import com.xihh.base.android.BaseApplication
import com.xihh.base.utils.LogUtil

class ComicApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        LogUtil.init(BuildConfig.DEBUG)
    }
}