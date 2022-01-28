fun main() {
    var (a, b) = readLine()!!.split(" ").map { it.toInt() }
    // Write only exchange actions here. Do not touch the lines above
    a -= b
    b += a
    a = -1 * (a - b)
    // Do not touch the lines below
    print("$a $b")
}