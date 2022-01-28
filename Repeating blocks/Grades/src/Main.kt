fun main() {
    val n = readLine()!!.toInt()
    var d = 0
    var c = 0
    var b = 0
    var a = 0
    var s: String
    repeat(n) {
        s = readLine()!!
        if (s == "5") a++ else if (s == "4") b++ else if (s == "3") c++ else d++
    }
    print("$d $c $b $a")
}