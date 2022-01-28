import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    var n = scanner.nextInt()
    var debit = 0
    while (n >= debit && scanner.hasNextInt()) {
        n -= debit
        debit = scanner.nextInt()
    }
    if (n < debit) {
        print("Error, insufficient funds for the purchase. Your balance is $n, but you need $debit.")
    } else {
        n -= debit
        print("Thank you for choosing us to manage your account! Your balance is $n.")
    }
}