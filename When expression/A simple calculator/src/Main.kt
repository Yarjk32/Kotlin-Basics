fun main() {
    val input = readLine()!!.split(' ').map { it }.toMutableList()
    val a = input[0].toLong()
    val op = input[1].first()
    val b = input[2].toLong()
    print(
        when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' ->
                if (b == 0L) {
                    "Division by 0!"
                } else {
                    a / b
                }
            else -> "Unknown operator"
        }
    )
}