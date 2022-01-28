fun main() {
    val word = readLine()!!
    val repet = word.length
    val result = word.repeat(repet)
    print("$repet repetitions of the word $word: $result")
}