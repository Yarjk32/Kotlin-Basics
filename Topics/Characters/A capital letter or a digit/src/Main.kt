fun main() {
    val c: Char = readLine()!!.first()
    print('A' <= c && c <= 'Z' || '1' <= c && c <= '9')
}