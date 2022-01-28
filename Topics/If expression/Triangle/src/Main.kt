fun main() {
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    val c = readLine()!!.toInt()
    print(if (a < b + c && b < a + c && c < a + b) "YES" else "NO")
}