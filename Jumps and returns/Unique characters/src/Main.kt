fun main() {
    val characters = MutableList(0) { ' ' }
    val counts = MutableList(0) { 0 }
    val str = readLine()!!
    var i: Int
    for (c in str) {
        if (!(c in characters)) {
            characters.add(c)
            counts.add(1)
        } else {
            i = 0
            while (characters[i] != c) i++
            counts[i]++
        }
    }
    var result = 0
    for (count in counts) result += if (count == 1) 1 else 0
    print(result)
}