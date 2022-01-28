fun main() {
    val number = readLine()!!.toInt()
    val left = readLine()!!.toInt()
    val right = readLine()!!.toInt()

    val inRange = number >= left && number <= right || number >= right && number <= left

    println(inRange)
}
