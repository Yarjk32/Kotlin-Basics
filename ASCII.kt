package signature
import kotlin.math.max
import java.io.File

const val WIDEWIDTH = 6
const val STANDARDWIDTH = 5
const val TWIDTH = 4
const val JWIDTH = 3
const val BORDERSWIDTH = 8
const val ROMANSIZE = 10
const val BETWEENLETTER = 11

fun fin(): MutableList<String> {
    val result = MutableList(0) { "" }
    File("ASCII Text Signature/task/src/signature/roman.txt").forEachLine { result.add(it) }
    return result
}

fun line(length: Int, symbol: Char): String {
    repeat(length) {
        print(symbol)
    }
    return ""
}

fun letterIndex(letter: Char, font: MutableList<String>): Int {
    var i = 0
    while (font[i * BETWEENLETTER + 1].first() != letter) i++
    return i * BETWEENLETTER + 1
}

fun getRomanLength(text: String, font: MutableList<String>): Int {
    var result = 0
    for (letter in text) {
        result += if (letter == ' ') ROMANSIZE else font[letterIndex(letter, font)].substring(2).toInt()
    }
    return result
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
    return result
}

fun startLine(space: Int) {
    print("88  ")
    repeat(space) {
        print(' ')
    }
}

fun romanLine(row: Int, text: String, font: MutableList<String>): String {
    var result = ""
    for (letter in text) {
        result += when (letter) {
            ' ' -> "          "
            else -> font[letterIndex(letter, font) + row]
        }
    }
    return result
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
    return result
}

fun changeLine(space: Int, isShifted: Int) {
    repeat(space + isShifted) {
        print(' ')
    }
    print("  88\n88  ")
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
    return result
}

fun endLine(space: Int, isShifted: Int) {
    repeat(space + isShifted) {
        print(' ')
    }
    println("  88")
}

fun romanPrint(text: String, bigLength: Int, yourLength: Int, font: MutableList<String>) {
    val beginSpace = if (yourLength < bigLength) (bigLength - yourLength) / 2 else 0
    val isShifted = if (yourLength >= bigLength || yourLength % 2 == bigLength % 2) 0 else 1
    startLine(beginSpace)
    for (i in 1 until ROMANSIZE) {
        print(romanLine(i, text, font))
        changeLine(beginSpace, isShifted)
    }
    print(romanLine(10, text, font))
    endLine(beginSpace, isShifted)
}

fun bigPrint(text: String, yourLength: Int, romaLength: Int) {
    val beginSpace = if (yourLength < romaLength) (romaLength - yourLength) / 2 else 0
    val isShifted = if (yourLength >= romaLength || yourLength % 2 == romaLength % 2) 0 else 1
    startLine(beginSpace)
    print(firstLine(text))
    changeLine(beginSpace, isShifted)
    print(secondLine(text))
    changeLine(beginSpace, isShifted)
    print(thirdLine(text))
    endLine(beginSpace, isShifted)
}

fun main() {
    val font = fin()
    print("Enter name and surname: ")
    val romanText = readLine()!!
    print("Enter person's status: ")
    val bigText = readLine()!!.uppercase()
    val romanLength = getRomanLength(romanText, font)
    val bigLength = getBigLength(bigText)
    val fullLength = max(bigLength, romanLength) + BORDERSWIDTH
    println(line(fullLength, '8'))
    romanPrint(romanText, bigLength, romanLength, font)
    bigPrint(bigText, bigLength, romanLength)
    print(line(fullLength, '8'))
}
