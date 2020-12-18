package com.cslx.greenheart.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.cslx.greenheart.R

class TitleView : RelativeLayout{
    @JvmField
    @BindView(R.id.title_rl)
    var title_rl: RelativeLayout? = null
    @JvmField
    @BindView(R.id.title_tv)
    var title_tv: TextView? = null
    @JvmField
    @BindView(R.id.back_tv)
    var back_tv: TextView? = null
    @JvmField
    @BindView(R.id.right_iv)
    var right_iv: ImageView? = null
    @JvmField
    @BindView(R.id.v_line)
    var line: View? = null

    private var mContext : Context? = null

    constructor(context:Context) : super(context) {
        mContext = context;
        initView()
    }
    constructor(context:Context,attrs:AttributeSet) : super(context,attrs) {
        mContext = context;
        initView()
    }
    constructor(context:Context,attrs:AttributeSet,defStyleAttr:Int) : super(context,attrs,defStyleAttr) {
        mContext = context;
        initView()
    }

    fun initView(){
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.layout_titleview,null)
        ButterKnife.bind(view)

        line?.visibility = View.GONE
        mContext?.resources?.getColor(R.color.themecolor)?.let { title_rl?.setBackgroundColor(it) }
    }

    fun setTitle(text:String){
        title_tv?.setText(text)
    }

    fun setLineBackground(color:Int){
        line?.setBackgroundColor(color)
        line?.setVisibility(View.VISIBLE)
    }

    fun setBackImageResource(res: Int) {
        val drawableLeft = resources.getDrawable(res)
        drawableLeft.setBounds(0, 0, drawableLeft.minimumWidth, drawableLeft.minimumHeight)
        back_tv?.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
    }

    fun setBackListener(listener:OnClickListener){
        if(listener!=null){
            back_tv?.setVisibility(View.VISIBLE)
            back_tv?.setOnClickListener(listener)
        }
    }

    fun setBgColor(color: Int) {
        title_rl?.setBackgroundColor(color)
    }

    fun setTitleTextColor(color: Int) {
        title_tv?.setTextColor(color)
    }

    fun setBackVisibility(res: Int) {
        back_tv?.setVisibility(res)
    }

    fun setRightInVisibility(res: Int) {
        right_iv?.setVisibility(res)
    }

    fun setRightListener(resid: Int, listener: View.OnClickListener) {
        right_iv?.setImageResource(resid)
        right_iv?.setVisibility(View.VISIBLE)
        right_iv?.setOnClickListener(listener)
    }
}