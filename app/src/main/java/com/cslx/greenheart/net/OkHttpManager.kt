package com.cslx.greenheart.net

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.cslx.greenheart.net.callback.OkHttpCallBack
import com.cslx.greenheart.view.dialog.CustomWaitDialog
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpManager {
    companion object{
        private var mContext : Context? = null
        @Volatile private var instance : OkHttpManager? = null
        fun getInstance(context : Context) : OkHttpManager? {
            mContext = context
            instance ?: synchronized(this){
                instance ?: OkHttpManager()
            }
            return instance
        }
    }

    private val TAG : String = "OkHttpManager"
    private var mHandler : Handler? = null
    private var mHttpClient : OkHttpClient? = null

    private constructor() {
        mHandler = Handler(Looper.getMainLooper())
        mHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
//                .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
//                .addInterceptor(new RedirectInterceptor())
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
            .build()
    }

//    String url, Map<String, String> params, final Class<?> cla, final CustomWaitDialog dialog, final OkHttpCallBack<T> callback
    fun <T> postRequest(url :String,params :MutableMap<String,String>,cla : Class<T>,dialog :CustomWaitDialog,callback : OkHttpCallBack<T>){

    }
}