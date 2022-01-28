const val THREE = 3

fun even(n: Int) = n % 2 == 0

fun seq(n: Int) {
    print("$n ")
    if (n != 1) {
        seq(if (even(n)) n / 2 else THREE * n + 1)
    }
}

fun main() {
    val n = readLine()!!.toInt()
    seq(n)
}
