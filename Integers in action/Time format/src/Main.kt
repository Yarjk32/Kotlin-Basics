const val THOUSAND = 1000
const val SECINDAY = 86_400
const val SIXTY = 60
const val SECINHOUR = 3600

fun main() {
    val totalSeconds = System.currentTimeMillis() / THOUSAND // dont change this line
    print(totalSeconds % SECINDAY / SECINHOUR)
    print(':')
    print(totalSeconds % SECINDAY / SIXTY % SIXTY)
    print(':')
    print(totalSeconds % SIXTY)
}