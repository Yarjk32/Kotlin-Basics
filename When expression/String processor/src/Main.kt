fun main() {
    val a = readLine()!!
    val op = readLine()!!
    val b = readLine()!!
    print(
        when (op) {
            "equals" -> a == b
            "plus" -> a + b
            "endsWith" -> a.endsWith(b)
            else -> "Unknown operation"
        }
    )
}
