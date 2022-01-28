fun main() {
    var max = 0
    var now: Int
    do {
        now = readLine()!!.toInt()
        max = Math.max(max, now)
    } while (now != 0)
    print(max)
}
