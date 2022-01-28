fun main() {
    val minSleep = readLine()!!.toInt()
    val maxSleep = readLine()!!.toInt()
    val actualSleep = readLine()!!.toInt()
    print(if (actualSleep < minSleep) "Deficiency" else if (actualSleep > maxSleep) "Excess" else "Normal")
}