package adventofcode2020.day07

import adventofcode2020.readFileLineByLine

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {
    val data = mutableMapOf<String, MutableList<String>>()
    readFileLineByLine("input07") {
        val line = it.replace("bags", "").replace("bag", "").replace(".", "").replace(" ", "")
        val rule = line.split("contain")
        val parent = rule.first()
        val childs = rule.last().filter { !it.isDigit() }.split(",")
        data.put(parent, childs.toMutableList())
    }
    val count = countOfParentsWith(data, "shinygold")
    println("Answer is $count")
}

private fun taskB() {
    val data = mutableMapOf<String, MutableList<Pair<Int, String>>>()
    readFileLineByLine("input07") {
        val line = it.replace("bags", "").replace("bag", "").replace(".", "").replace(" ", "")
        val rule = line.split("contain")
        val parent = rule.first()
        val children = rule.last().map {
            if (it.isDigit()) {
                "$it#"
            } else {
                it.toString()
            }
        }.joinToString("").split(",")
            .map {
                val temp = it.split("#").toMutableList()
                if (temp.size == 1)
                    temp.add(0, "0")
                Pair(temp.first().toInt(), temp.last())
            }
        data.put(parent, children.toMutableList())
    }
    val count = getEmptyChildrenUnderParent(data, "shinygold")
//    println(data)
    println("Answer is $count")
}

fun countOfParentsWith(data: Map<String, List<String>>, child: String): Int {
    var count = 0
    val parent = mutableSetOf<String>()
    val history = mutableSetOf<String>()
    parent.addAll(data.filter { it.value.contains(child) }.map { it.key })
    count += parent.count()

    while (parent.isNotEmpty() && count != 0) {
        val tempPar = parent.toMutableList()
        history.addAll(parent)
        parent.clear()
        tempPar.forEach { key ->
            parent.addAll(data.filter { it.value.contains(key) }.map { it.key })
        }
        parent.removeIf { history.contains(it) }
        count += parent.count()
    }
    return count
}

fun getEmptyChildrenUnderParent(data: Map<String, List<Pair<Int, String>>>, mainParent: String): Int {
    var count = 0
    data.filter { it.key == mainParent }.map { it.value }.forEach { children ->
        children.forEach { item ->
            count += item.first * childCount(data, item)
        }
    }
    return count
}

private fun childCount(data: Map<String, List<Pair<Int, String>>>, pItem: Pair<Int, String>): Int {
    var count = 1
    data[pItem.second]?.forEach { item ->
        count += (item.first * childCount(data, item))
    }
    return count

}