package com.cslx.greenheart.base

import android.os.Environment

class Constants {
    companion object{
        val DOWNLOAD_FILENAME : String = "safetyme.apk"
        val DOWNLOAD_PATH : String = Environment.getExternalStorageDirectory().getPath() + "/safetyme/"
        val WECHAT_APPID : String = "wxfaf75663d7f72e30"
        val WECHAT_SETRET : String = "2eeab595d8f8fdc344f82afa3b156ced"
        val ALIPAY_APPID : String = "2021001191626067"
        val FRAGMENT_JUMP : String = "com.cslx.greenheart.fragmentjump"
    }
}