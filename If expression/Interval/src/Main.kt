const val A = -15
const val B = 12
const val C = 14
const val D = 17
const val E = 19

fun main() {
    val x = readLine()!!.toInt()
    print(if (A < x && x <= B || C < x && x < D || E <= x) "True" else "False")
}