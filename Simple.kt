/********************************************************************************************
*  ____ ____ ____ _ _      ___ ____ _  _ ___      ____ _ ____ _  _ ____ ___ _  _ ____ ____  *
*  |__| [__  |    | |       |  |___  \/   |       [__  | | __ |\ | |__|  |  |  | |__/ |___  *
*  |  | ___] |___ | |       |  |___ _/\_  |       ___] | |__] | \| |  |  |  |__| |  \ |___  *
*                                      simple version                                       *
********************************************************************************************/
import kotlin.math.max

const val WIDEWIDTH = 6
const val STANDARDWIDTH = 5
const val TWIDTH = 4
const val JWIDTH = 3

fun line(length: Int, symbol: Char): String {
    repeat(length) {
        print(symbol)
    }
    return ""
}

fun getBigLength(text: String): Int {
    var result = 0
    for (letter in text) {
        result += when (letter) {
            'I' -> 2
            'J' -> JWIDTH
            'T' -> TWIDTH
            'W', 'Y' -> WIDEWIDTH
            else -> STANDARDWIDTH
        }
    }
    return --result
}

fun startLine(space: Int) {
    print("*  ")
    repeat(space) {
        print(' ')
    }
}

fun firstLine(text: String): String {
    var result = ""
    for (letter in text) {
        result += when (letter) {
            ' ' -> "     "
            'B', 'D', 'P', 'Z' -> "___  "
            'H', 'K', 'M', 'N', 'U', 'V', 'X' -> "_  _ "
            'I' -> "_ "
            'J' -> " _ "
            'L' -> "_    "
            'T' -> "___ "
            'W' -> "_ _ _ "
            'Y' -> "_   _ "
            else -> "____ "
        }
    }
    result = result.substring(0, result.lastIndex)
    return result
}

fun changeLine(space: Int, isShifted: Int) {
    repeat(space + isShifted) {
        print(' ')
    }
    print("  *\n*  ")
    repeat(space) {
        print(' ')
    }
}

fun secondLine(text: String): String {
    var result = ""
    for (letter in text) {
        result += when (letter) {
            ' ' -> "     "
            'A', 'H' -> "|__| "
            'B', 'P' -> "|__] "
            'C', 'L' -> "|    "
            'D' -> "|  \\ "
            'E', 'F' -> "|___ "
            'G' -> "| __ "
            'I' -> "| "
            'J' -> " | "
            'K' -> "|_/  "
            'M' -> "|\\/| "
            'N' -> "|\\ | "
            'R' -> "|__/ "
            'S' -> "[__  "
            'T' -> " |  "
            'W' -> "| | | "
            'X' -> " \\/  "
            'Y' -> " \\_/  "
            'Z' -> "  /  "
            else -> "|  | "
        }
    }
    result = result.substring(0, result.lastIndex)
    return result
}

fun thirdLine(text: String): String {
    var result = ""
    for (letter in text) {
        result += when (letter) {
            ' ' -> "     "
            'B', 'G' -> "|__] "
            'C', 'E', 'L' -> "|___ "
            'D' -> "|__/ "
            'F', 'P' -> "|    "
            'I' -> "| "
            'J' -> "_| "
            'K' -> "| \\_ "
            'N' -> "| \\| "
            'O', 'U' -> "|__| "
            'Q' -> "|_\\| "
            'R' -> "|  \\ "
            'S' -> "___] "
            'T' -> " |  "
            'V' -> " \\/  "
            'W' -> "|_|_| "
            'X' -> "_/\\_ "
            'Y' -> "  |   "
            'Z' -> " /__ "
            else -> "|  | "
        }
    }
    result = result.substring(0, result.lastIndex)
    return result
}

fun endLine(space: Int, isShifted: Int) {
    repeat(space + isShifted) {
        print(' ')
    }
    println("  *")
}

fun bigPrint(text: String, yourLength: Int, smaLength: Int) {
    val beginSpace = if (yourLength < smaLength) (smaLength - yourLength) / 2 else 0
    val isShifted = if (yourLength >= smaLength || yourLength % 2 == smaLength % 2) 0 else 1
    startLine(beginSpace)
    print(firstLine(text))
    changeLine(beginSpace, isShifted)
    print(secondLine(text))
    changeLine(beginSpace, isShifted)
    print(thirdLine(text))
    endLine(beginSpace, isShifted)
}

fun smallPrint(text: String, bigLength: Int, yourLength: Int) {
    val beginSpace = if (yourLength < bigLength) (bigLength - yourLength) / 2 else 0
    val isShifted = if (yourLength >= bigLength || yourLength % 2 == bigLength % 2) 0 else 1
    startLine(beginSpace)
    print(text)
    endLine(beginSpace, isShifted)
}

fun main() {
    print("Enter name and surname: ")
    val bigText = readLine()!!.uppercase()
    print("Enter person's status: ")
    val smallText = readLine()!!
    val bigLength = getBigLength(bigText)
    val smaLength = smallText.length
    val fullLength = max(bigLength, smaLength) + WIDEWIDTH
    println(line(fullLength, '*'))
    bigPrint(bigText, bigLength, smaLength)
    smallPrint(smallText, bigLength, smaLength)
    print(line(fullLength, '*'))
}