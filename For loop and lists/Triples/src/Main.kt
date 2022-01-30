fun main() {
    val n = readLine()!!.toInt()
    if (n < 3) {
        print(0)
    } else {
        var result = 0
        var first = readLine()!!.toInt()
        var second = readLine()!!.toInt()
        var third: Int
        repeat(n - 2) {
            third = readLine()!!.toInt()
            if (first + 1 == second && first + 2 == third) result++
            first = second
            second = third
        }
        print(result)
    }
}