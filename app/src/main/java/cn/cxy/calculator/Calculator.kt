package cn.cxy.calculator

import android.util.Log

class Calculator(val resultCallback: ResultCallback) {
    fun accept(input: String) {
        Log.v("dddd", input)
        resultCallback.updateResult(input)
        resultCallback.updateTempResult(input)
    }
}

interface ResultCallback {
    fun updateResult(text: String)
    fun updateTempResult(text: String)
}