fun main() {    
    val firstAnswer = readLine().toBoolean()
    val secondAnswer = readLine().toBoolean()
    val confession = readLine().toBoolean()
    
    print(!(firstAnswer xor secondAnswer || confession)) // literally looking for the conditions from the problem
}
