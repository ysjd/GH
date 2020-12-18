package com.cslx.greenheart.util

import android.util.Log

class LogUtil {
    companion object{
        var debug : Boolean = true;
        fun i(tag:String,msg:String){
            if(debug){
                Log.i(tag,msg)
            }
        }
    }
}