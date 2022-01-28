import kotlin.math.max

const val FOUR = 4

fun main() {
    val n = readLine()!!.toInt()
    var max4 = 0
    var nToCheck = 0
    repeat(n) {
        nToCheck = readLine()!!.toInt()
        if (nToCheck % FOUR == 0) max4 = max(max4, nToCheck)
    }
    print(max4)
}