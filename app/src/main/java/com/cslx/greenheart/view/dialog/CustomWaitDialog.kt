package com.cslx.greenheart.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.cslx.greenheart.R

class CustomWaitDialog : Dialog{
    @JvmField
    @BindView(R.id.waitTextView)
    var waitTextView : TextView? = null

    private var mContext : Context? = null
    private var mOnKeyCancel : OnKeyCancel? = null

    private var FLAG : Boolean = false

    constructor(context: Context) : super(context){
        init(context)
    }

    constructor(context: Context, theme: Int) : super(context,theme){
        init(context)
    }

    constructor(context: Context, cancelable: Boolean,cancelListener: DialogInterface.OnCancelListener) : super(context,cancelable,cancelListener){
        init(context)
    }

    fun init(context: Context){
        mContext = context
        setContentView(R.layout.dialog_custom_wait)
        ButterKnife.bind(this)
    }


    fun setTextColor(color: Int) {
        waitTextView?.setTextColor(color)
    }

    override fun setTitle(titleId: Int) {
        setTitle(mContext?.getString(titleId))
    }

    override fun setTitle(title: CharSequence?) {
        setMessage(title.toString())
    }

    fun setMessage(messageString: String) {
        waitTextView?.setText(messageString)
    }

    fun setOnKeyCancelListener(onKeyCancel: OnKeyCancel) {
        mOnKeyCancel = onKeyCancel
    }

    interface OnKeyCancel {
        fun onKeyCancelListener()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            this.dismiss()
            mOnKeyCancel?.onKeyCancelListener()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun setFlag(b:Boolean){
        FLAG = b
    }

    fun fullScreenImmersive(view : View){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            var uiOptions: Int = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            view.setSystemUiVisibility(uiOptions)
        }
    }

    override fun show() {
        if (FLAG) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
        }
        super.show()
        if (FLAG) {
            window?.decorView?.let { fullScreenImmersive(it) }
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }
}