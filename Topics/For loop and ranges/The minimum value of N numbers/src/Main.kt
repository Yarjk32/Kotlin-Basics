import kotlin.math.min

fun main() {
    val n = readLine()!!.toInt()
    var minN = Int.MAX_VALUE
    var nToCheck = 0
    repeat(n) {
        nToCheck = readLine()!!.toInt()
        minN = min(minN, nToCheck)
    }
    print(minN)
}