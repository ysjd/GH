package com.cslx.greenheart

import android.app.Activity
import android.app.Application
import com.cslx.greenheart.base.Constants
import com.cslx.greenheart.util.LogUtil
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class MyApplication : Application(){
    companion object{
        var application : MyApplication? = null
        fun getInstance() : MyApplication? {
            return application
        }
    }

    private val TAG : String = "MyApplication"
    var wxapi : IWXAPI? = null
    var activity_list : MutableList<Activity> = mutableListOf<Activity>()

    override fun onCreate() {
        super.onCreate()
        application = this

        wxapi = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APPID,false)
        wxapi!!.registerApp(Constants.WECHAT_APPID)
    }

    fun addActivity(activity: Activity){
        activity_list.add(activity)
    }

    fun removeActivity(activtiy: Activity){
       activity_list.remove(activtiy)
    }

    fun finishAllActivity(){
        for(activity in activity_list){
            activity.finish()
        }
        activity_list.clear()
    }
}