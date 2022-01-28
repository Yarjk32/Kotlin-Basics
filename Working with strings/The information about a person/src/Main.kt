fun main() {
    val (name, surname, age) = readLine()!!.split(" ")
    print("${name[0]}. $surname, $age years old")
}