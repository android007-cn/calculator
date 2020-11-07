package cn.cxy.calculator

class Calculator(private val resultCallback: ResultCallback) {
    var inputStringBuffer = StringBuffer()

    fun accept(input: String) {
        var tempResult = ""
        var result = ""
        when (input) {
            KEY_CLEAR -> clear()
            KEY_DEL -> deleteLastInput()
            KEY_ADD, KEY_SUB, KEY_MULTIPLY, KEY_DIV -> doOps(input)
            KEY_GET_RESULT -> result = getResult()
            else -> append(input)
        }
        if (result.isNotEmpty()) {
            inputStringBuffer.replace(0, inputStringBuffer.length, result)
        } else {
            tempResult = getResult()
        }
        resultCallback.updateResult(inputStringBuffer.toString())
        resultCallback.updateTempResult(tempResult)
    }

    private fun getResult(): String {
        val inputString = inputStringBuffer.toString()
        if (inputString.isEmpty()) {
            return ""
        }
        var numOrOpList = spitIntoList(inputString)
        //为便于后续计算，如果最后一个元素是操作符，则删除掉。
        if (isOp(numOrOpList.last())) {
            numOrOpList.remove(numOrOpList.last())
        }
        return calculate(numOrOpList).toString()
    }

    private fun calculate(numOrOpList: MutableList<String>): Long {
        if (numOrOpList.size == 1) {
            return numOrOpList[0].toLong()
        }
        var opIndex = -1
        opIndex = numOrOpList.indexOf(KEY_MULTIPLY)
        if (opIndex != -1) {
            val lastNum = numOrOpList[opIndex - 1]
            val nextNum = numOrOpList[opIndex + 1]
            val result = lastNum.toLong() * nextNum.toLong()
            val newList = replaceThreeElementsByOne(numOrOpList, opIndex, result.toString())
            return calculate(newList)
        }

        opIndex = numOrOpList.indexOf(KEY_DIV)
        if (opIndex != -1) {
            val lastNum = numOrOpList[opIndex - 1]
            val nextNum = numOrOpList[opIndex + 1]
            val result = lastNum.toLong() / nextNum.toLong()
            val newList = replaceThreeElementsByOne(numOrOpList, opIndex, result.toString())
            return calculate(newList)
        }

        opIndex = numOrOpList.indexOf(KEY_ADD)
        if (opIndex != -1) {
            val lastNum = numOrOpList[opIndex - 1]
            val nextNum = numOrOpList[opIndex + 1]
            val result = lastNum.toLong() + nextNum.toLong()
            val newList = replaceThreeElementsByOne(numOrOpList, opIndex, result.toString())
            return calculate(newList)
        }

        opIndex = numOrOpList.indexOf(KEY_SUB)
        if (opIndex != -1) {
            val lastNum = numOrOpList[opIndex - 1]
            val nextNum = numOrOpList[opIndex + 1]
            val result = lastNum.toLong() - nextNum.toLong()
            val newList = replaceThreeElementsByOne(numOrOpList, opIndex, result.toString())
            return calculate(newList)
        }

        return 0
    }


    /**
     * 拆分字符串为列表，如1+2*3+4拆分为：
     * 1
     * +
     * 2
     * *
     * 3
     * +
     * 4
     */
    private fun spitIntoList(inputString: String): MutableList<String> {
        var list = mutableListOf<String>()
        var startIndex = 0
        for (index in inputString.indices) {
            if (!isDigit(inputString.elementAt(index).toString())) {
                list.add(inputString.substring(startIndex, index))
                list.add(inputString.elementAt(index).toString())
                startIndex = index + 1
            } else if (index == inputString.length - 1) {
                list.add(inputString.substring(startIndex, index + 1))
            }
        }
        return list
    }

    private fun doOps(input: String) {
        val lastInput = getLastInput()
        if (isDigit(lastInput)) {
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