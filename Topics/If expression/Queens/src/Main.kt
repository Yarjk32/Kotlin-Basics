fun main() {
    val (s1x, s1y) = readLine()!!.split(" ")
    val (s2x, s2y) = readLine()!!.split(" ")
    val x1 = s1x.toInt()
    val y1 = s1y.toInt()
    val x2 = s2x.toInt()
    val y2 = s2y.toInt()
    print(if (x1 == x2 || y1 == y2 || Math.abs(x2 - x1) == Math.abs(y2 - y1)) "YES" else "NO")
}