fun main() {
    val nums: MutableList<Int> = mutableListOf()
    val n = readLine()!!.toInt()
    repeat(n) {
        nums.add(readLine()!!.toInt())
    }
    val (a, b) = readLine()!!.split(' ').map { it.toInt() }
    var first: Int
    var second = nums[0]
    var i = 1
    do {
        first = second
        second = nums[i]
        i++
    } while (!(first == a && second == b || first == b && second == a) && i < nums.size)
    if (first == a && second == b || first == b && second == a) {
        print("NO")
    } else {
        print("YES")
    }
}
