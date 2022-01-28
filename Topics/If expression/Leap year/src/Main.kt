const val NORMALEAP = 4
const val NOTLEAP = 100
const val SUPERLEAP = 400

fun main() {
    val year = readLine()!!.toInt()
    print(if (year % SUPERLEAP == 0 || year % NORMALEAP == 0 && year % NOTLEAP != 0) "Leap" else "Regular")
}