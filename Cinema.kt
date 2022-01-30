package cinema

const val SEATSLIMIT = 60
const val VIPRICE = 10
const val POORPRICE = 8

fun inputRoom(): String {
    println("Enter the number of rows:")
    val r = readLine()!!
    println("Enter the number of seats in each row:")
    val s = readLine()!!
    return "$r $s"
}

fun scheme(cinema: MutableList<MutableList<Char>>) {
    print("\nCinema:\n ")
    for (i in 1..cinema[0].size) print(" $i")
    println()
    for (i in 0 until cinema.size) println("${i + 1} ${cinema[i].joinToString(" ")}")
}

fun inputSeat(): String {
    println("\nEnter a row number:")
    val r = readLine()!!
    println("Enter a seat number in that row:")
    val s = readLine()!!
    return "$r $s"
}

fun smallRoom() = VIPRICE

fun bigRoom(rows: Int, seatR: Int) = if (seatR <= rows / 2) VIPRICE else POORPRICE

fun calculateSeat(cinema: MutableList<MutableList<Char>>, seatR: Int): Int {
    if (cinema.size * cinema[0].size <= SEATSLIMIT) {
        return smallRoom()
    }
    return bigRoom(cinema.size, seatR)
}

fun shopping(cinema: MutableList<MutableList<Char>>) {
    val (seatR, seatS) = inputSeat().split(" ").map { it.toInt() }
    println()
    if (seatR < 1 || seatR > cinema.size || seatS < 1 || seatS > cinema[0].size) {
        println("Wrong input!")
        shopping(cinema)
        return
    }
    when (cinema[seatR - 1][seatS - 1]) {
        'S' -> {
            cinema[seatR - 1][seatS - 1] = 'B'
            println("Ticket price: \$${calculateSeat(cinema, seatR)}")
        }
        'B' -> {
            println("That ticket has already been purchased!")
            shopping(cinema)
        }
    }
}

fun calculateAll(cinema: MutableList<MutableList<Char>>): Int {
    var result = 0
    for (row in 0 until  cinema.size) for (seat in cinema[row]) result += calculateSeat(cinema, row + 1)
    return result
}

fun stats(cinema: MutableList<MutableList<Char>>) {
    var bought = 0
    var income = 0
    for (row in cinema) for (seat in row) bought += if (seat == 'B') 1 else 0
    println("\nNumber of purchased tickets: $bought")
    var notPrc = bought * 100000 / (cinema.size * cinema[0].size)
    notPrc = (notPrc + if (notPrc % 10 > 4) 10 else 0) / 10
    val prc = (notPrc / 100).toString() + "." + (notPrc % 100 / 10).toString() + (notPrc % 10).toString()
    println("Percentage: ${prc}%")
    for (row in 0 until  cinema.size) for (seat in cinema[row]) income += if (seat == 'B') calculateSeat(cinema, row + 1) else 0
    println("Current income: \$$income")
    println("Total income: \$${calculateAll(cinema)}")
}

fun main() {
    val (rows, seats) = inputRoom().split(" ").map { it.toInt() }
    val cinema = MutableList(rows) { MutableList(seats) { 'S' } }
    var choice = -1
    while (choice != 0) {
        println()
        println("""
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
        """.trimIndent())
        choice = readLine()!!.toInt()
        when (choice) {
            1 -> scheme(cinema)
            2 -> shopping(cinema)
            3 -> stats(cinema)
        }
    }
}