package cn.cxy.calculator

/**
 * 将列表中连续的三个元素替换为一个元素
 * 用于实现计算结果替换，如原先的元素是：1 + 2，替换为3
 */
fun replaceThreeElementsByOne(
    numOrOpList: MutableList<String>,
    middleElementPosition: Int,
    replacement: String
): MutableList<String> {
    val newList = mutableListOf<String>()
    newList.addAll(numOrOpList)
    newList.removeAt(middleElementPosition - 1)
    newList.removeAt(middleElementPosition - 1)
    newList.removeAt(middleElementPosition - 1)
    newList.add(middleElementPosition - 1, replacement)
    return newList
}