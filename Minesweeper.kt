package minesweeper
import kotlin.random.Random

const val SIDE = 9

class Input(val x: Int, val y: Int, val action: String)

class Cell(val mine: Boolean) {
    var explored = false
    var tag = false
    var number = 0
    var zone = -1
    val zones: MutableSet<Int> = mutableSetOf()

    fun isDot() = ! this.explored

    fun isTag() = this.tag

    fun isMine() = this.mine

    fun isNumber() = this.number > 0

    fun explore() {
        this.explored = true
        this.tag = false
    }

    fun changeTag() {
        if (this.isDot()) {
            this.tag = ! this.tag
        }
    }

    fun copyTag(source: Cell) {
        this.tag = source.isTag()
    }

    fun setNum(n: Int) {
        this.number = n
        this.zone = 0
    }

    fun bad4Tag() = this.mine xor this.tag

    fun toExplore() = ! (this.mine || this.explored)
}

fun emptyField(): MutableList<MutableList<Cell>> {
    val result = emptyList<MutableList<Cell>>().toMutableList()
    for (i in 1..SIDE) {
        result.add(emptyList<Cell>().toMutableList())
        for (j in 1..SIDE) result[result.lastIndex].add(Cell(false))
    }
    return result
}

fun getInput(): Input {
    print("Set/unset mines marks or claim a cell as free: ")
    val input = readLine()!!.split(' ').map { it }.toList()
    return Input(input[1].toInt() - 1, input[0].toInt() - 1, input[2])
}

fun printField(field: MutableList<MutableList<Cell>>) {
    println("\n |123456789|\n—|—————————|")
    for (i in 0 until SIDE) {
        print("${i + 1}|")
        for (j in 0 until SIDE) {
            if (field[i][j].isTag()) {
                print('*')
            } else if (field[i][j].isDot()) {
                print('.')
            } else if (field[i][j].isNumber()) {
                print(field[i][j].number)
            } else {
                print('/')
            }
        }
        println('|')
    }
    println("—|—————————|")
}

fun makeSet(size: Int): Set<Int> {
    val to = SIDE * SIDE
    val set = mutableSetOf<Int>()
    while (set.size < size) {
        set.add(Random.nextInt(to))
    }
    return set
}

fun putMines(minesNumber: Int, to: MutableList<MutableList<Cell>>): MutableList<MutableList<Cell>> {
    val mines: Set<Int> = makeSet(minesNumber)
    val result = emptyList<MutableList<Cell>>().toMutableList()
    for (i in 0 until SIDE) {
        result.add(emptyList<Cell>().toMutableList())
        for (j in 0 until SIDE) {
            result[i].add(Cell(i * SIDE + j in mines))
            result[i][j].copyTag(to[i][j])
        }
    }
    return result
}

fun makeNumbers(field: MutableList<MutableList<Cell>>): MutableList<MutableList<Cell>> {
    for (i in 0 until SIDE) for (j in 0 until SIDE) {
        if (! field[i][j].isMine()) {
            var n = 0
            for (x in i - 1..i + 1) for (y in j - 1..j + 1) {
                if (x in 0 until SIDE && y in 0 until SIDE && field[x][y].isMine()) {
                    n++
                }
            }
            if (n > 0) {
                field[i][j].setNum(n)
            }
        }
    }
    return field
}

fun marksNotNums(field: MutableList<MutableList<Cell>>): Boolean {
    for (i in 0 until SIDE) for (j in 0 until SIDE) {
        if (field[i][j].isNumber() && field[i][j].isTag()) {
            return true
        }
    }
    return false
}

fun makeZones(field: MutableList<MutableList<Cell>>): MutableList<MutableList<Cell>> {
    var n = 0
    for (i in 0 until SIDE) for (j in 0 until SIDE) {
        n++
        if (field[i][j].zone == -1) {
            val q = mutableListOf(intArrayOf(i, j))
            val toBe = arrayOf(
                arrayOf(true, true, true, true, true, true, true, true, true),
                arrayOf(true, true, true, true, true, true, true, true, true),
                arrayOf(true, true, true, true, true, true, true, true, true),
                arrayOf(true, true, true, true, true, true, true, true, true),
                arrayOf(true, true, true, true, true, true, true, true, true),
                arrayOf(true, true, true, true, true, true, true, true, true),
                arrayOf(true, true, true, true, true, true, true, true, true),
                arrayOf(true, true, true, true, true, true, true, true, true),
                arrayOf(true, true, true, true, true, true, true, true, true)
            )
            toBe[i][j] = false
            while (q.isNotEmpty()) {
                if (q[0][0] < SIDE - 1 && toBe[q[0][0] + 1][q[0][1]] && field[q[0][0] + 1][q[0][1]].zone != 0) {
                    q.add(intArrayOf(q[0][0] + 1, q[0][1]))
                    toBe[q[0][0] + 1][q[0][1]] = false
                }
                if (q[0][1] < SIDE - 1 && toBe[q[0][0]][q[0][1] + 1] && field[q[0][0]][q[0][1] + 1].zone != 0) {
                    q.add(intArrayOf(q[0][0], q[0][1] + 1))
                    toBe[q[0][0]][q[0][1] + 1] = false
                }
                if (q[0][0] > 0 && toBe[q[0][0] - 1][q[0][1]] && field[q[0][0] - 1][q[0][1]].zone != 0) {
                    q.add(intArrayOf(q[0][0] - 1, q[0][1]))
                    toBe[q[0][0] - 1][q[0][1]] = false
                }
                if (q[0][1] > 0 && toBe[q[0][0]][q[0][1] - 1] && field[q[0][0]][q[0][1] - 1].zone != 0) {
                    q.add(intArrayOf(q[0][0], q[0][1] - 1))
                    toBe[q[0][0]][q[0][1] - 1] = false
                }
                field[q[0][0]][q[0][1]].zone = n
                q.removeAt(0)
            }
        }
    }
    return field
}

fun expandZones(field: MutableList<MutableList<Cell>>): MutableList<MutableList<Cell>> {
    for (i in 0 until SIDE) for (j in 0 until SIDE) {
        if (field[i][j].number > 0) {
            for (x in i - 1..i + 1) for (y in j - 1..j + 1) {
                if (x in 0 until SIDE && y in 0 until SIDE && field[x][y].zone > 0) {
                    field[i][j].zones.add(field[x][y].zone)
                }
            }
        }
    }
    return field
}

fun minesAreTagged(field: MutableList<MutableList<Cell>>): Boolean {
    for (i in 0 until SIDE) for (j in 0 until SIDE) {
        if (field[i][j].bad4Tag()) {
            return false
        }
    }
    return true
}

fun openZone(field: MutableList<MutableList<Cell>>, zone: Int): MutableList<MutableList<Cell>> {
    for (i in 0 until SIDE) for (j in 0 until SIDE) {
        if (field[i][j].zone == zone || zone in field[i][j].zones) {
            field[i][j].explore()
        }
    }
    return field
}

fun allExplored(field: MutableList<MutableList<Cell>>): Boolean {
    for (i in 0 until SIDE) for (j in 0 until SIDE) {
        if (field[i][j].toExplore()) {
            return false
        }
    }
    return true
}

fun defeat(field: MutableList<MutableList<Cell>>) {
    println("\n |123456789|\n—|—————————|")
    for (i in 0 until SIDE) {
        print("${i + 1}|")
        for (j in 0 until SIDE) {
            if (field[i][j].isMine()) {
                print('X')
            } else if (field[i][j].isTag()) {
                print('*')
            } else if (field[i][j].isDot()) {
                print('.')
            } else if (field[i][j].isNumber()) {
                print(field[i][j].number)
            } else {
                print('/')
            }
        }
        println('|')
    }
    print("—|—————————|\nYou stepped on a mine and failed!")
}

fun main() {
    print("How many mines do you want on the field? ")
    val minesNumber = readLine()!!.toInt()

    val start = emptyField()
    printField(start)
    var act = Input(0, 0, "")
    while (act.action != "free") {
        do {
            act = getInput()
        } while (act.action !in listOf("free", "mine"))
        if (act.action == "mine") {
            start[act.x][act.y].changeTag()
            printField(start)
        }
    }
    var field: MutableList<MutableList<Cell>>
    do {
        do {
            field = putMines(minesNumber, start)
        } while (field[act.x][act.y].isMine())
        field = makeNumbers(field)
    } while (marksNotNums(field))
    field = makeZones(field)
    field = expandZones(field)
    if (field[act.x][act.y].isNumber()) {
        field[act.x][act.y].explore()
    } else {
        field = openZone(field, field[act.x][act.y].zone)
    }

    if (allExplored(field)) {
        printField(field)
        print("Congratulations! You found all the mines!")
    } else {
        val victory: Boolean
        while (true) {
            printField(field)
            do {
                act = getInput()
            } while (act.action !in listOf("free", "mine"))
            if (act.action == "mine") {
                field[act.x][act.y].changeTag()
                if (minesAreTagged(field)) {
                    victory = true
                    break
                }
            } else {
                if (field[act.x][act.y].isMine()) {
                    victory = false
                    break
                }
                if (field[act.x][act.y].isNumber()) {
                    field[act.x][act.y].explore()
                } else {
                    field = openZone(field, field[act.x][act.y].zone)
                }
                if (allExplored(field)) {
                    victory = true
                    break
                }
            }
        }
        if (victory) {
            printField(field)
            print("Congratulations! You found all the mines!")
        } else {
            defeat(field)
        }
    }
}