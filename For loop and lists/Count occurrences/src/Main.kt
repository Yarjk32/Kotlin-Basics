fun main() {
    val nums: MutableList<Int> = mutableListOf()
    val n = readLine()!!.toInt()
    var occurs = 0
    repeat(n) {
        nums.add(readLine()!!.toInt())
    }
    val toFind = readLine()!!.toInt()
    for (el in nums) if (el == toFind) occurs++
    print(occurs)
}
