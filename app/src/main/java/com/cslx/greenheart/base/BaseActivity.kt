package com.cslx.greenheart.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.cslx.greenheart.MyApplication
import com.cslx.greenheart.R
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity : AppCompatActivity(){
    private var binder : Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.themecolor).navigationBarColor(
            R.color.white).keyboardEnable(true).init()
        setContentView(getContentViewResId())
        binder = ButterKnife.bind(this)
        init(savedInstanceState)
        MyApplication.getInstance()?.addActivity(this)
    }

    abstract fun init(savedInstanceState :Bundle?)

    abstract fun getContentViewResId() : Int

    override fun onDestroy() {
        super.onDestroy()
        binder?.unbind()
        MyApplication.getInstance()?.removeActivity(this)
    }
}