fun main() {
    val n = readLine()!!.toInt()
    var positives = 0
    repeat(n) {
        if (readLine()!!.toInt() > 0) positives++
    }
    print(positives)
}