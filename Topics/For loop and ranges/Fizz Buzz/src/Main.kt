const val FIZZ = 3
const val BUZZ = 5

fun main() {
    val s = readLine()!!.toInt()
    val e = readLine()!!.toInt()
    for (n in s..e) {
        if (n % FIZZ == 0 || n % BUZZ == 0) {
            if (n % FIZZ == 0) print("Fizz")
            if (n % BUZZ == 0) print("Buzz")
        } else print(n)
        println()
    }
}