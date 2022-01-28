import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    var next: Int
    var max = Int.MIN_VALUE
    var maxI = 0
    var i = 0
    while (scanner.hasNextInt()) {
        i++
        next = scanner.nextInt()
        if (next > max) {
            max = next
            maxI = i
        }
    }
    print("$max $maxI")
}