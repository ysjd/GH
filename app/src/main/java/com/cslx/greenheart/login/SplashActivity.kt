package com.cslx.greenheart.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cslx.greenheart.MainActivity
import com.cslx.greenheart.R
import com.cslx.greenheart.base.BaseActivity
import com.cslx.greenheart.util.LogUtil
import com.cslx.greenheart.util.SharedPreferenceUtil

class SplashActivity : BaseActivity(){
    private val TAG : String = "SplashActivity"
    private var mPermissionList: MutableList<String> = mutableListOf()
    private var mHandler : Handler = Handler(Looper.getMainLooper())
    private var mPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA)
    private val PERMISSION_REQUESTCODE : Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init(savedInstanceState: Bundle?) {
        LogUtil.i(TAG,"ininininininini")
        if(VERSION.SDK_INT>=23){
            initPermission()
        }else{
            goin()
        }
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_splash
    }

    //进入主界面
    private fun goin(){
        mHandler.postDelayed(Runnable{
            var userid : String? = SharedPreferenceUtil.getInstance(this)?.getUserId()
            if(userid != null && !"".equals(userid)){
                var intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                var intent: Intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

        },3*1000)
    }

    //请求权限
    private fun initPermission(){
        mPermissionList.clear()
        for(permission in mPermissions){
            if(ContextCompat.checkSelfPermission(this,permission)!=PackageManager.PERMISSION_GRANTED){
                mPermissionList.add(permission)
            }
        }
        if(mPermissionList.size>0){
            var permission_arr : Array<String> = mPermissionList.toTypedArray()
            ActivityCompat.requestPermissions(this@SplashActivity,permission_arr,PERMISSION_REQUESTCODE)
        }else{
            goin()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==PERMISSION_REQUESTCODE){
            var hasPermissionDismiss : Boolean = false

            for(i in 0 until permissions.size){
                LogUtil.i(TAG,"permissions.size"+permissions.size)
                if(grantResults[i]==-1){
                    hasPermissionDismiss = true
                    break;
                }
            }
            if(hasPermissionDismiss){
                //有权限没有允许
            }else{
                goin()
            }
        }
    }

}