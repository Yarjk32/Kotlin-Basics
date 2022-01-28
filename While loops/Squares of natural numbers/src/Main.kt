fun main() {
    var n = readLine()!!.toInt()
    n = Math.sqrt(n.toDouble()).toInt()
    for (i in 1..n) println(i * i)
}
