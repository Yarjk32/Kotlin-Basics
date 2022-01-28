fun main() {
    val x = readLine().toBoolean() // read other values in the same way
    val y = readLine().toBoolean() // read other values in the same way
    val z = readLine().toBoolean() // read other values in the same way
    print(!(x && y) || z)
}