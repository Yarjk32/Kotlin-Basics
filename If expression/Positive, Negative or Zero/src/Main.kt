fun main() {
    val n = readLine()!!.toInt()
    print(if (n < 0) "negative" else if (n == 0) "zero" else "positive")
}