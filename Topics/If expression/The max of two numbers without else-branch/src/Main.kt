fun main() {

    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()

    val dif = a - b > 0
    print(if (dif) a else b)
}