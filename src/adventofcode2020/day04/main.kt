package adventofcode2020.day04

import adventofcode2020.readFileLineByLine
import java.util.regex.Matcher
import java.util.regex.Pattern

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {
    val inputs = mutableListOf<Boolean>()
    val temp = HashMap<String, String>()
    readFileLineByLine("input04") {
        if (it.isBlank() || it.isEmpty()) {
            val isValid = temp.containsKey("byr") &&
                    temp.containsKey("iyr") &&
                    temp.containsKey("eyr") &&
                    temp.containsKey("hgt") &&
                    temp.containsKey("hcl") &&
                    temp.containsKey("ecl") &&
                    temp.containsKey("pid")
//                    && temp.containsKey("cid")
            inputs.add(isValid)
            temp.clear()
        } else {
            it.split(" ").associate {
                val (left, right) = it.split(":")
                left to right
            }.let {
                temp.putAll(it)
            }
        }
    }
    println("Answer is ${inputs.filter { it }.count()}")
}

private fun taskB() {
    val inputs = mutableListOf<Boolean>()
    val temp = HashMap<String, String>()
    readFileLineByLine("input04") {
        if (it.isBlank() || it.isEmpty()) {
            val isValid = temp.containsKey("byr") && temp["byr"]?.toIntOrNull() ?: 0 in 1920..2002 &&
                    temp.containsKey("iyr") && temp["iyr"]?.toIntOrNull() ?: 0 in 2010..2020 &&
                    temp.containsKey("eyr") && temp["eyr"]?.toIntOrNull() ?: 0 in 2020..2030 &&
                    temp.containsKey("hgt") &&
                    (checkHeightInCm(temp["hgt"]) || checkHeightInIn(temp["hgt"])) &&
                    temp.containsKey("hcl") &&
                    handleHairColor(temp["hcl"]) &&
                    temp.containsKey("ecl") &&
                    handleEyeColor(temp["ecl"]) &&
                    temp.containsKey("pid") &&
                    temp["pid"]?.length == 9 &&
                    temp["pid"]?.toIntOrNull() != null
//                    && temp.containsKey("cid")
            inputs.add(isValid)
            temp.clear()
        } else {
            it.split(" ").associate {
                val (left, right) = it.split(":")
                left to right
            }.let {
                temp.putAll(it)
            }
        }
    }
    println("Answer is ${inputs.filter { it }.count()}")
}

private fun checkHeightInCm(height: String?): Boolean {
    if (height?.contains("cm") != true) return false
    val num = height.removeRange(height.length - 2, height.length).toIntOrNull()
    return if (num in 150..193) true else {
        println("height in cm $height")
        false
    }
}

private fun checkHeightInIn(height: String?): Boolean {
    if (height?.contains("in") != true) return false
    val num = height.removeRange(height.length - 2, height.length).toIntOrNull()
    return if (num in 59..76) true else {
        println("height in inch $height")
        false
    }
}

private fun handleHairColor(color: String?): Boolean {
    if (color?.contains("#") != true)
        return false
    val colorPattern: Pattern = Pattern.compile("^#([a-f]|[0-9]){6}\$")
    val m: Matcher = colorPattern.matcher(color)
    return if (m.matches())
        true
    else {
        println("Color $color")
        false
    }
}

private fun handleEyeColor(color: String?): Boolean {
    val approved = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    return if (approved.contains(color))
        true else {
        println("eye Color $color")
        false
    }
}


