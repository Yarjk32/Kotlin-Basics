fun main() {
    var n = readLine()!!.toInt()
    var sorted = true
    var before = readLine()!!.toInt()
    var now: Int
    repeat(--n) {
        now = readLine()!!.toInt()
        if (before > now) sorted = false
        before = now
    }
    print(if (sorted) "YES" else "NO")
}