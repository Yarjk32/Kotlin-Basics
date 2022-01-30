fun main() {
    val mas = MutableList(0) { "" }
    val n = readLine()!!.toInt()
    repeat(n) {
        mas.add(readLine()!!)
    }
    val shift = 1
    val firstPart = mas.subList(mas.size - shift, mas.size)
    val secondPart = mas.subList(0, mas.size - shift)
    print((firstPart + secondPart).joinToString(" "))
}
