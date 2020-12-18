package com.cslx.greenheart.net.callback

interface OkHttpCallBack<T> {
    fun OnError(e : Exception)
    fun OnSuccess(obj : T)
}