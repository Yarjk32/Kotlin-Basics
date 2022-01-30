fun main() {
    val str = readLine()!!
    var i = 0
    while (!(str[i] in '0'..'9')) i++
    print(str[i])
}