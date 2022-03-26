package chess

import kotlin.math.abs

class Pawn(var file: Int, var rank: Int) {
    var canBeEnPassanted = false

    fun move(toFile: Int, toRank: Int) {
        file = toFile
        rank = toRank
    }
}

open class Team(val pawns: MutableList<Pawn>, val movesUp: Boolean) {
    fun hasPawnAt(file: Int, rank: Int): Boolean {
        for (pawn in pawns) {
            if (pawn.file == file && pawn.rank == rank) {
                return true
            }
        }
        return false
    }

    fun getPawn(file: Int, rank: Int): Pawn {
        for (pawn in pawns) {
            if (pawn.file == file && pawn.rank == rank) {
                return pawn
            }
        }
        throw Exception("No pawn found at ${'`' + file}$rank")
    }

    fun lose(victim: Pawn) {
        pawns.remove(victim)
    }

    fun unEnPassant() {
        for (pawn in pawns) pawn.canBeEnPassanted = false
    }

    fun movePawn(fromFile: Int, fromRank: Int, toFile: Int, toRank: Int) {
        val pawn = getPawn(fromFile, fromRank)
        pawn.move(toFile, toRank)
        if (
            fromRank == 2 && toRank == 4 ||
            fromRank == 7 && toRank == 5
        ) {
            pawn.canBeEnPassanted = true
        }
    }

    fun enPassant(loser: Team, fromFile: Int, toFile: Int): Boolean {
        loser.lose(loser.getPawn(toFile, if (loser.movesUp) 4 else 5))
        movePawn(fromFile, if (movesUp) 5 else 4, toFile, if (movesUp) 6 else 3)
        return true
    }

    fun capture(loser: Team, fromFile: Int, fromRank: Int, toFile: Int, toRank: Int): Boolean {
        loser.lose(loser.getPawn(toFile, toRank))
        movePawn(fromFile, fromRank, toFile, toRank)
        return true
    }

    fun tryToCapture(defender: Team, fromFile: Int, fromRank: Int, toFile: Int, toRank: Int, execute: Boolean): Boolean {
        if (abs(toFile - fromFile) != 1) {
            return false
        }
        if (movesUp && fromRank == 5 && toRank == 6 || !movesUp && fromRank == 4 && toRank == 3) {
            if (defender.hasPawnAt(toFile, if (movesUp) 5 else 4)) {
                val victim = defender.getPawn(toFile, if (movesUp) 5 else 4)
                if (victim.canBeEnPassanted) {
                    return if (execute) enPassant(defender, fromFile, toFile)
                        else true
                }
            }
        }
        if (!defender.hasPawnAt(toFile, toRank)) {
            return false
        }
        if (movesUp) {
            if (toRank - fromRank == 1) {
                return if (execute) capture(defender, fromFile, fromRank, toFile, toRank)
                    else true
            }
            return false
        }
        if (fromRank - toRank == 1) {
            return if (execute) capture(defender, fromFile, fromRank, toFile, toRank)
                else true
        }
        return false
    }

    fun destroyed(): Boolean {
        return pawns.isEmpty()
    }
}

object White : Team(mutableListOf(
    Pawn(1, 2), Pawn(2, 2),
    Pawn(3, 2), Pawn(4, 2),
    Pawn(5, 2), Pawn(6, 2),
    Pawn(7, 2), Pawn(8, 2)), true) {
    fun tryToCapture(coords: List<Int>): Boolean {
        return tryToCapture(Black, coords[0], coords[1], coords[2], coords[3], true)
    }
}

object Black : Team(mutableListOf(
    Pawn(1, 7), Pawn(2, 7),
    Pawn(3, 7), Pawn(4, 7),
    Pawn(5, 7), Pawn(6, 7),
    Pawn(7, 7), Pawn(8, 7)), false) {
    fun tryToCapture(coords: List<Int>): Boolean {
        return tryToCapture(White, coords[0], coords[1], coords[2], coords[3], true)
    }
}

fun showBoard() {
    for (rank in 8 downTo 1) {
        print("  +---+---+---+---+---+---+---+---+\n$rank | ")
        for (file in 1..8) {
            print("${
                if (White.hasPawnAt(file, rank)) 'W' else if (Black.hasPawnAt(file, rank)) 'B' else ' '
            } | ")
        }
        println()
    }
    println("  +---+---+---+---+---+---+---+---+\n    a   b   c   d   e   f   g   h\n")
}

fun parseCoords(move: String): List<Int> {
    val result = MutableList(0) { 0 }
    for (char in move) {
        if (char in 'a'..'h') {
            result.add(char - '`')
        } else {
            result.add(char - '0')
        }
    }
    return result
}

fun invalidInput(move: String): Boolean {
    return !move.matches(Regex("[a-h][1-8][a-h][1-8]"))
}

fun badMove(coords: List<Int>, whiteTurn: Boolean): Boolean {
    if (White.hasPawnAt(coords[2], coords[3]) || Black.hasPawnAt(coords[2], coords[3])) {
        return true
    }
    if (coords[0] != coords[2]) {
        return true
    }
    if (whiteTurn) {
        if (coords[1] > coords[3]) {
            return true
        }
    } else {
        if (coords[3] > coords[1]) {
            return true
        }
    }
    return if (coords[1] == (if (whiteTurn) 2 else 7)) {
        if ((if (whiteTurn) Black else White).hasPawnAt(coords[0], if (whiteTurn) 3 else 6)) {
            return true
        }
        abs(coords[3] - coords[1]) !in (1..2)
    } else {
        abs(coords[3] - coords[1]) != 1
    }
}

fun canContinue(nextWhite: Boolean): Boolean {
    if (White.destroyed()) {
        println("Black Wins!")
        return false
    }
    if (Black.destroyed()) {
        println("White Wins!")
        return false
    }
    for (pawn in White.pawns) {
        if (pawn.rank == 8) {
            println("White Wins!")
            return false
        }
    }
    for (pawn in Black.pawns) {
        if (pawn.rank == 1) {
            println("Black Wins!")
            return false
        }
    }
    for (pawn in (if (nextWhite) White else Black).pawns) {
        if (!badMove(listOf(pawn.file, pawn.rank, pawn.file, pawn.rank + if (nextWhite) 1 else -1), nextWhite)) {
            return true
        }
        if (pawn.rank == if (nextWhite) 2 else 7) {
            if (!badMove(listOf(pawn.file, if (nextWhite) 2 else 7, pawn.file, if (nextWhite) 4 else 5), nextWhite)) {
                return true
            }
        }
        if ((if (nextWhite) White else Black).tryToCapture(
                defender = if (nextWhite) Black else White,
                fromFile = pawn.file,
                fromRank = pawn.rank,
                toFile = pawn.file - 1,
                toRank = pawn.rank + if (nextWhite) 1 else -1,
                execute = false)) {
            return true
        }
        if ((if (nextWhite) White else Black).tryToCapture(
                defender = if (nextWhite) Black else White,
                fromFile = pawn.file,
                fromRank = pawn.rank,
                toFile = pawn.file + 1,
                toRank = pawn.rank + if (nextWhite) 1 else -1,
                execute = false)) {
            return true
        }
    }
    println("Stalemate!")
    return false
}

fun main() {
    println("Pawns-Only Chess")

    println("First Player's name:")
    val name1 = readLine()!!
    println("Second Player's name:")
    val name2 = readLine()!!

    showBoard()
    var whiteTurn = true
    do {
        (if (whiteTurn) White else Black).unEnPassant()
        println("${if (whiteTurn) name1 else name2}'s turn:")
        val move = readLine()!!
        if (move == "exit") {
            break
        }
        if (invalidInput(move)) {
            println("Invalid Input")
        } else {
            val coords = parseCoords(move)
            if (!(if (whiteTurn) White else Black).hasPawnAt(coords[0], coords[1])) {
                println("No ${if (whiteTurn) "white" else "black"} pawn at ${move.substring(0, 2)}")
            } else {
                if (whiteTurn && White.tryToCapture(coords)) {
                    whiteTurn = false
                    showBoard()
                } else if (!whiteTurn && Black.tryToCapture(coords)) {
                    whiteTurn = true
                    showBoard()
                } else if (badMove(coords, whiteTurn)) {
                    println("Invalid Input")
                } else {
                    (if (whiteTurn) White else Black).movePawn(coords[0], coords[1], coords[2], coords[3])
                    whiteTurn = !whiteTurn
                    showBoard()
                }
            }
        }
    } while (canContinue(whiteTurn))
    print("Bye!")
}