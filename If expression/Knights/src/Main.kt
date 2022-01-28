const val ONE = 1
const val TWO = 2

fun main() {
    val (sx1, sy1) = readLine()!!.split(" ")
    val (sx2, sy2) = readLine()!!.split(" ")
    val x1 = sx1.toInt()
    val y1 = sy1.toInt()
    val x2 = sx2.toInt()
    val y2 = sy2.toInt()
    val xdif = Math.abs(x1 - x2)
    val ydif = Math.abs(y1 - y2)
    print(if (xdif == ONE && ydif == TWO || xdif == TWO && ydif == ONE) "YES" else "NO")
}