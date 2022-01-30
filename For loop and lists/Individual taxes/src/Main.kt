const val TOPERCENT = 100

fun main() {
    val n = readLine()!!.toInt()
    val income = MutableList(n) { readLine()!!.toInt() }
    val tax = MutableList(n) { readLine()!!.toDouble() / TOPERCENT }
    var pay = 0.0
    var payMax = 0.0
    var iMax = -1
    for (i in 0 until n) {
        pay = income[i] * tax[i]
        if (pay > payMax) {
            payMax = pay
            iMax = i + 1
        }
    }
    print(iMax)
}
