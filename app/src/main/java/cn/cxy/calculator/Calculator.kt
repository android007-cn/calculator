package cn.cxy.calculator

import android.util.Log

class Calculator(private val resultCallback: ResultCallback) {
    var inputStringBuffer = StringBuffer()
    fun accept(input: String) {
        when (input) {
            KEY_CLEAR -> clear()
            KEY_DEL -> deleteLastInput()
            KEY_ADD, KEY_SUB, KEY_MULTIPLY, KEY_DIV -> doOps(input)
            KEY_GET_RESULT -> getResult()
            else -> append(input)
        }
        resultCallback.updateResult(inputStringBuffer.toString())
        resultCallback.updateTempResult(input)
    }

    private fun getResult() {

    }

    private fun doOps(input: String) {
        val lastInput = getLastInput()
        if (isDigit(lastInput) || lastInput == KEY_PERCENT) {
            append(input)
        }
    }

    private fun append(input: String) {
        inputStringBuffer.append(input)
    }

    private fun clear() {
        inputStringBuffer.delete(0, inputStringBuffer.count())
    }

    private fun getLastInput(): String {
        var result = ""
        if (inputStringBuffer.count() > 0) {
            result = inputStringBuffer[inputStringBuffer.count() - 1].toString()
        }
        return result
    }

    private fun deleteLastInput() {
        if (inputStringBuffer.count() > 0) {
            inputStringBuffer.deleteCharAt(inputStringBuffer.count() - 1)
        }
    }

    private fun isDigit(input: String) = input in KEY_ZERO..KEY_NINE
    private fun isNoneZeroDigit(input: String) = input in KEY_ONE..KEY_NINE
    private fun isOp(input: String) =
        input == KEY_ADD || input == KEY_SUB || input == KEY_MULTIPLY || input == KEY_DIV
}

interface ResultCallback {
    fun updateResult(text: String)
    fun updateTempResult(text: String)
}