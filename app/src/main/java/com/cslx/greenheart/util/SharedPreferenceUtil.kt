package com.cslx.greenheart.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil {
    private var mContext : Context? = null
    private var sharedPreferences : SharedPreferences? = null
    private var mEditor : SharedPreferences.Editor? = null
    private val NAME : String = "greenheart"

    private constructor(context : Context){
        sharedPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)
        mEditor = sharedPreferences?.edit()
    }

    companion object{
        @Volatile private var instance : SharedPreferenceUtil? = null
        fun getInstance(context: Context) : SharedPreferenceUtil? {
            instance ?: synchronized(this){
                instance ?: SharedPreferenceUtil(context)
            }
            return instance
        }
    }

    fun setUserId(id : String){
        mEditor?.putString("userid",id)
        mEditor?.commit()
    }

    fun getUserId() : String?{
        return sharedPreferences?.getString("userid","")
    }
}