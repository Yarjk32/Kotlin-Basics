/**
 *  The hard way
 */
const val KNOWN = 2_147_483_647

fun main() {
    val positive = KNOWN
    println("32")
    val negative: Int = positive + 1
    println(negative)
    print(positive)
}