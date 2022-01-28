const val DIFF = 32

fun main() {
    val a: Char = readLine()!!.first()
    val b: Char = readLine()!!.first()
    val bigA = a - DIFF
    val bigB = b - DIFF
    print(a == b || bigA == b || a == bigB)
}