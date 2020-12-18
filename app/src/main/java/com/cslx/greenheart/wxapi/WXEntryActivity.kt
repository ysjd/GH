package com.cslx.greenheart.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.cslx.greenheart.MyApplication
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

class WXEntryActivity : Activity(),IWXAPIEventHandler {
    private val TAG : String = "WXEntryActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            if (!MyApplication.getInstance()!!.wxapi!!.handleIntent(intent, this)) {
                finish()
            }
        } catch (e: Exception) {

        }

    }

    override fun onResp(resp: BaseResp?) {
        if (resp!!.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            var launchMiniProResp : WXLaunchMiniProgram.Resp = resp as WXLaunchMiniProgram.Resp
            var extraData : String = launchMiniProResp.extMsg
            //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
            finish()
        }else{
            when(resp!!.errCode){
                BaseResp.ErrCode.ERR_OK ->{
                    if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                        var authResp : SendAuth.Resp = resp as SendAuth.Resp
                        val code = authResp.code
                        getAccessToken(code)
                    }
                }
                BaseResp.ErrCode.ERR_USER_CANCEL ->{
                    finish()
                }
                BaseResp.ErrCode.ERR_AUTH_DENIED ->{
                    finish()
                }
                BaseResp.ErrCode.ERR_UNSUPPORT ->{
                    finish()
                }
                else ->{
                    finish()
                }
            }
        }
    }

    override fun onReq(p0: BaseReq?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (!MyApplication.getInstance()!!.wxapi!!.handleIntent(intent, this)) {
            finish()
        }
    }

    private fun getAccessToken(code : String){

    }
}