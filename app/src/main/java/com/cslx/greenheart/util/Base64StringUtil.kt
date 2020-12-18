package com.cslx.greenheart.util

import java.util.*

class Base64StringUtil {
    companion object{
        val SOURCES : String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
        fun Base64Random() : String? {
            val random = Random()
            val text = CharArray(32)
            for(i in 0 until text.size){
                text[i] = SOURCES.get(random.nextInt(SOURCES.length))
            }
            val result = String(text)
            return Base64.encode(result.toByteArray())
        }
    }
}