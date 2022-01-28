fun main() {
    val n = readLine()!!.toInt()
    var str: String
    var a = 0
    var b = 0
    var c = 0
    repeat(n) {
        str = readLine()!!
        if (str == "-1") c++ else if (str == "0") a++ else b++
    }
    print("$a $b $c")
}