package adventofcode2020.day01


fun findPairBySum(inputs: MutableList<Int>, targetSum: Int): Pair<Int, Int>? {
    var num1: Int? = null
    while (inputs.isNotEmpty() && num1 == null) {
        val tempNum = inputs.removeFirst()
        if (inputs.firstOrNull { it == (targetSum - tempNum) } != null)
            num1 = tempNum
    }

    if (num1 == null) {
        return null
    }
    val num2 = targetSum - num1
    return Pair(num1, num2)
}