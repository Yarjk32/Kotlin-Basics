const val DIV2 = 2
const val DIV3 = 3
const val DIV5 = 5
const val DIV6 = 6

fun main() {
    val n = readLine()!!.toInt()
    print(if (n % DIV2 == 0) "Divided by 2\n" else "")
    print(if (n % DIV3 == 0) "Divided by 3\n" else "")
    print(if (n % DIV5 == 0) "Divided by 5\n" else "")
    print(if (n % DIV6 == 0) "Divided by 6\n" else "")
}