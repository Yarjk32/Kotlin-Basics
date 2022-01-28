const val TEN = 10
const val HDRD = 100

fun main() {
    val n3dig = readLine()!!.toInt()
    val sum = n3dig / HDRD + n3dig / TEN % TEN + n3dig % TEN
    print(sum)
}
