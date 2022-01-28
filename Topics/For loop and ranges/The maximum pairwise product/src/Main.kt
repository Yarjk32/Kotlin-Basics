fun main() {
    var n = readLine()!!.toInt()
    if (n > 1) {
        var first = readLine()!!.toInt()
        var second = 0
        var now: Int
        repeat(--n) {
            now = readLine()!!.toInt()
            if (now > first) {
                second = first
                first = now
            } else if (now > second) second = now
        }
        print(first * second)
    } else print(readLine()!!)
}