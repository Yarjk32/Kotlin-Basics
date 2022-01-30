import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    var coordinates = mutableListOf<Int>()
    val rows = MutableList(6) { true }
    val columns = MutableList(6) { true }
    while (scanner.hasNextInt()) {
        coordinates = mutableListOf(scanner.nextInt(), scanner.nextInt())
        rows[coordinates[0]] = false
        columns[coordinates[1]] = false
    }
    var first = true
    for (i in 1..5) {
        if (first && rows[i]) {
            print(i)
            first = false
        } else {
            print(if (rows[i]) " $i" else "")
        }
    }
    println()
    first = true
    for (i in 1..5) {
        if (first && columns[i]) {
            print(i)
            first = false
        } else {
            print(if (columns[i]) " $i" else "")
        }
    }
}