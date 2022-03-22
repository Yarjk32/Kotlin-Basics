package indigo
import kotlin.random.Random

enum class Rank(val value: Int) {
    RA(1),
    R2(2), R3(3), R4(4), R5(5),
    R6(6), R7(7), R8(8), R9(9),
    R10(10), RJ(11), RQ(12), RK(13);

    companion object {
        fun getRank(v: Int): String {
            var result = ""
            for (r in values()) if (v == r.value) result = r.name
            return result.substring(1)
        }
    }
}

enum class Suit(val symbol: Char) {
    S1('♠'), S2('♥'), S3('♦'), S4('♣');

    companion object {
        fun getSymbol(v: Int): Char = valueOf("S$v").symbol
    }
}

data class Card(val rank: Int, val suit: Int) {
    override fun toString(): String {
        return "${Rank.getRank(rank)}${Suit.getSymbol(suit)}"
    }
}

open class TakeOnly {
    var cards = MutableList(0) { Card(0, 0) }

    fun getTopCard(): Card {
        return cards[0]
    }

    fun removeTopCard() {
        cards.removeAt(0)
    }

    fun isEmpty(): Boolean {
        return cards.size == 0
    }
}

open class TakeAndGet: TakeOnly() {
    fun takeCard() {
        cards.add(Deck.getTopCard())
        Deck.removeTopCard()
    }

    open fun getCards(): String {
        var result = ""
        for (i in 0..cards.lastIndex) {
            result += "${cards[i]} "
        }
        return result
    }
}

open class Gamer : TakeAndGet() {
    var score = 0
    var wonCards = 0

    fun numberOfCards(): Int {
        return cards.size
    }

    fun moveCard(number: Int) {
        Table.addCard(cards.removeAt(number - 1))
    }

    fun winsCards() {
        for (card in Table.cards) {
            wonCards++
            if (card.rank >= 10 || card.rank == 1) {
                score++
            }
        }
        Table.clearTable()
    }
}

object Deck : TakeOnly() {
    fun reset() {
        cards.removeAll(cards)
        for (j in 4 downTo 1) for (i in 13 downTo 1) cards.add(Card(i, j))
    }

    fun shuffle() {
        val newDeck = MutableList(0) { Card(0, 0) }
        while (cards.isNotEmpty()) {
            val i = Random.nextInt(cards.size)
            newDeck.add(cards[i])
            cards.removeAt(i)
        }
        cards = newDeck
    }
}

object Table : TakeAndGet() {
    fun addCard(card: Card) {
        cards.add(card)
    }

    fun status(): String {
        if (isEmpty()) {
            return "No cards on the table"
        }
        return "${cards.size} cards on the table, and the top card is ${cards[cards.lastIndex]}"
    }

    fun hasWinner(): Boolean {
        return cards.size > 1 && sameSuitOrRank(cards[cards.lastIndex], cards[cards.lastIndex - 1])
    }

    fun clearTable() {
        cards.removeAll(cards)
    }
}

object Player : Gamer() {
    override fun getCards(): String {
        var result = ""
        for (i in 0..cards.lastIndex) {
            result += "${i + 1})${cards[i]} "
        }
        return result
    }
}

object Computer : Gamer() {
    fun finishTurn(card: Card): Card {
        for (i in 0..cards.lastIndex) {
            if (card == cards[i]) {
                moveCard(i + 1)
                return card
            }
        }
        return card
    }

    fun makeTurn(): Card {
        if (numberOfCards() == 1) {
            return finishTurn(getTopCard())
        }

        if (Table.isEmpty()) {
            for (i in 0..cards.lastIndex) for (j in i + 1..cards.lastIndex) {
                if (cards[i].suit == cards[j].suit) {
                    return finishTurn(cards[i])
                }
            }

            for (i in 0..cards.lastIndex) for (j in i + 1..cards.lastIndex) {
                if (cards[i].rank == cards[j].rank) {
                    return finishTurn(cards[i])
                }
            }

            return finishTurn(getTopCard())
        }

        val candidateIndexes = MutableList(0) { 0 }
        for (i in 0..cards.lastIndex) {
            if (sameSuitOrRank(cards[i], Table.cards[Table.cards.lastIndex])) {
                candidateIndexes.add(i)
            }
        }

        if (candidateIndexes.size == 1) {
            return finishTurn(cards[candidateIndexes[0]])
        }

        if (candidateIndexes.size == 0) {
            for (i in 0..cards.lastIndex) for (j in i + 1..cards.lastIndex) {
                if (cards[i].suit == cards[j].suit) {
                    return finishTurn(cards[i])
                }
            }

            for (i in 0..cards.lastIndex) for (j in i + 1..cards.lastIndex) {
                if (cards[i].rank == cards[j].rank) {
                    return finishTurn(cards[i])
                }
            }

            return finishTurn(getTopCard())
        }

        for (i in 0..candidateIndexes.lastIndex) for (j in i + 1..candidateIndexes.lastIndex) {
            if (cards[candidateIndexes[i]].suit == cards[candidateIndexes[j]].suit) {
                return finishTurn(cards[candidateIndexes[i]])
            }
        }

        for (i in 0..candidateIndexes.lastIndex) for (j in i + 1..candidateIndexes.lastIndex) {
            if (cards[candidateIndexes[i]].rank == cards[candidateIndexes[j]].rank) {
                return finishTurn(cards[candidateIndexes[i]])
            }
        }

        return finishTurn(cards[candidateIndexes[0]])
    }
}

fun sameSuitOrRank(a: Card, b: Card): Boolean {
    return a.rank == b.rank || a.suit == b.suit
}

fun showScore() {
    println("Score: Player ${Player.score} - Computer ${Computer.score}")
    println("Cards: Player ${Player.wonCards} - Computer ${Computer.wonCards}")
}

fun gameEnd(): Boolean {
    return Deck.isEmpty() && Player.isEmpty() && Computer.isEmpty()
}

fun finalPoints(cardsForPlayer: Boolean, playerFirst: Boolean) {
    if (cardsForPlayer) {
        Player.winsCards()
    } else {
        Computer.winsCards()
    }
    if (Player.wonCards == Computer.wonCards) {
        if (playerFirst) {
            Player.score += 3
        } else {
            Computer.score += 3
        }
    } else if (Player.wonCards > Computer.wonCards) {
        Player.score += 3
    } else {
        Computer.score += 3
    }
}

fun main() {
    println("Indigo Card Game")
    var answer: String
    do {
        println("Play first?")
        answer = readLine()!!.lowercase()
    } while (answer != "yes" && answer != "no")
    var playerMove = answer == "yes"
    var lastWonPlayer = playerMove

    Deck.reset()
    Deck.shuffle()
    repeat(4) {
        Table.takeCard()
    }
    repeat(6) {
        Player.takeCard()
        Computer.takeCard()
    }
    println("Initial cards on the table: ${Table.getCards()}")
    do {
        println("\n${Table.status()}")
        var action = ""
        if (playerMove) {
            var chosen = 0
            if (Player.numberOfCards() == 0) {
                repeat(6) {
                    Player.takeCard()
                }
            }
            println("Cards in hand: ${Player.getCards()}")
            do {
                println("Choose a card to play (1-${Player.numberOfCards()}):")
                action = readLine()!!
                if (action == "exit") {
                    break
                }
                chosen = action.toIntOrNull() ?: 0
            } while (chosen == 0 || chosen > Player.numberOfCards())
            if (action != "exit") {
                Player.moveCard(chosen)
                if (Table.hasWinner()) {
                    Player.winsCards()
                    println("Player wins cards")
                    showScore()
                    lastWonPlayer = true
                }
            }
        } else {
            if (Computer.numberOfCards() == 0) {
                repeat(6) {
                    Computer.takeCard()
                }
            }
            println(Computer.getCards())
            val computerCard = Computer.makeTurn()
            println("Computer plays $computerCard")
            if (Table.hasWinner()) {
                Computer.winsCards()
                println("Computer wins cards")
                showScore()
                lastWonPlayer = false
            }
        }
        playerMove = ! playerMove
    } while (!gameEnd() && action != "exit")
    if (gameEnd()) {
        println("\n${Table.status()}")
        finalPoints(lastWonPlayer, playerMove)
        showScore()
    }
    print("Game Over")
}