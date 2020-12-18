package com.cslx.greenheart.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnCheckedChanged
import butterknife.OnClick
import com.cslx.greenheart.MyApplication
import com.cslx.greenheart.R
import com.cslx.greenheart.base.BaseActivity
import com.cslx.greenheart.util.Base64StringUtil
import com.cslx.greenheart.util.LogUtil
import com.cslx.greenheart.view.customview.TitleView
import com.cslx.greenheart.view.dialog.CustomWaitDialog
import com.tencent.mm.opensdk.modelmsg.SendAuth

class LoginActivity : BaseActivity(){
    @JvmField
    @BindView(R.id.titleview)
    var titleView: TitleView? = null
    @JvmField
    @BindView(R.id.iv_head)
    var iv_head: ImageView? = null
    @JvmField
    @BindView(R.id.tv_appname)
    var tv_appname: TextView? = null
    @JvmField
    @BindView(R.id.tv_appname2)
    var tv_appname2: TextView? = null
    @JvmField
    @BindView(R.id.iv_wechat)
    var iv_wechat: ImageView? = null
    @JvmField
    @BindView(R.id.iv_alipay)
    var iv_alipay: ImageView? = null
    @JvmField
    @BindView(R.id.cb_protocol)
    var cb_cb_protocol: CheckBox? = null
    @JvmField
    @BindView(R.id.tv_protocol)
    var tv_protocol: TextView? = null
    @JvmField
    @BindView(R.id.tv_secret)
    var tv_secret: TextView? = null
    @JvmField
    @BindView(R.id.tv_free_login)
    var tv_free_login: TextView? = null

    private val TAG : String = "LoginActivity"
    private var read_protocol : Boolean = false
    private var model : String = "1"
    private var dialog : CustomWaitDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_login
    }

    @OnClick(R.id.iv_wechat,R.id.iv_alipay,R.id.tv_protocol,R.id.tv_secret,R.id.tv_free_login)
    fun onclick(v: View){
        when(v.id){
            R.id.iv_wechat ->{
                if(!read_protocol){
                    Toast.makeText(this@LoginActivity, "请勾选同意用户协议，即可进入哦", Toast.LENGTH_SHORT).show()
                    return
                }
                model = "3"
                wechat_login()
            }
            R.id.iv_alipay ->{
                if (!read_protocol) {
                    Toast.makeText(this@LoginActivity, "请勾选同意用户协议，即可进入哦", Toast.LENGTH_SHORT).show()
                    return
                }
                model = "2"
                alipay_login()
            }
        }
    }

    @OnCheckedChanged(R.id.cb_protocol)
    fun onCheckChanged(b: Boolean){
        if (b) {
            read_protocol = true
        } else {
            read_protocol = false
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        var openid = intent!!.getStringExtra("openid")
        var nickname = intent!!.getStringExtra("nickname")
        var headimgurl = intent!!.getStringExtra("headimgurl")
        var unionid = intent!!.getStringExtra("unionid")

        login(model,null,openid,nickname,headimgurl,unionid)
    }

    private fun wechat_login(){
        if(!MyApplication.getInstance()!!.wxapi!!.isWXAppInstalled){
            Toast.makeText(this@LoginActivity, "您的设备未安装微信客户端", Toast.LENGTH_SHORT).show()
        }else{
            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = Base64StringUtil.Base64Random().toString()
            MyApplication.getInstance()?.wxapi?.sendReq(req)
        }
    }

    private fun alipay_login(){

    }

    /**
     * 登录
     * @param usertype
     * @param authCode
     * @param userid
     * @param username
     * @param avatar
     */
    private fun login(usertype: String,authCode: String?,userid: String,username: String,avatar: String,unionid: String){
        LogUtil.i(TAG,"login----")
    }
}