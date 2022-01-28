fun main() {
    var n = readLine()!!.toInt()
    if (n > 1) {
        var first = readLine()!!.toInt()
        var second: Int
        var row = 1
        var maxRow = 1
        repeat(--n) {
            second = readLine()!!.toInt()
            if (second >= first) {
                row++
            } else {
                maxRow = Math.max(maxRow, row)
                row = 1
            }
            first = second
        }
        maxRow = Math.max(maxRow, row)
        print(maxRow)
    } else print(n)
}
