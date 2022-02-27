package converter
import java.math.BigInteger
import java.math.BigDecimal
import java.math.RoundingMode

enum class Digit(val value: Int) {
    D0(0), D1(1), D2(2), D3(3), D4(4), D5(5),
    D6(6), D7(7), D8(8), D9(9), DA(10), DB(11),
    DC(12), DD(13), DE(14), DF(15), DG(16), DH(17),
    DI(18), DJ(19), DK(20), DL(21), DM(22), DN(23),
    DO(24), DP(25), DQ(26), DR(27), DS(28), DT(29),
    DU(30), DV(31), DW(32), DX(33), DY(34), DZ(35);

    companion object {
        fun getValue(d: Char): Int = valueOf("D$d").value

        fun getDigit(v: Int): String {
            var result = ""
            for (d in values()) if (v == d.value) result = d.name
            return result.substring(1)
        }
    }
}

class BasedNumber(val number: String, val base: Int) {
    fun toBase(newBase: Int): BasedNumber {
        var decimalValue = BigInteger.ZERO
        for (d in number) decimalValue = decimalValue * base.toBigInteger() + Digit.getValue(d).toBigInteger()

        var newNumber = ""
        while (decimalValue > BigInteger.ZERO) {
            newNumber += Digit.getDigit((decimalValue.remainder(newBase.toBigInteger())).toInt())
            decimalValue = decimalValue.divide(newBase.toBigInteger())
        }
        if (newNumber == "") newNumber = "0"
        return BasedNumber(newNumber.reversed(), newBase)
    }
}

class BasedFraction(val number: String, val base: Int) {
    fun toBase(newBase: Int): BasedFraction {
        var decimalValue = BigDecimal.ZERO
        var i = 1L
        for (d in number) {
            i *= base
            decimalValue += Digit.getValue(d).toBigDecimal().setScale(32, RoundingMode.FLOOR) / i.toBigDecimal()
        }
        decimalValue = decimalValue.setScale(32, RoundingMode.FLOOR)

        var newNumber = ""
        repeat(5) {
            decimalValue *= newBase.toBigDecimal()
            val intPart = decimalValue.setScale(0, RoundingMode.FLOOR)
            newNumber += Digit.getDigit(intPart.toInt())
            decimalValue -= intPart
        }
        return BasedFraction(newNumber, newBase)
    }
}

fun main() {
    do {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
        val baseLayer = readLine()!!
        if (baseLayer != "/exit") {
            val bases = baseLayer.split(" ").map { it.toInt() }.toIntArray()
            do {
                print("Enter number in base ${bases[0]} to convert to base ${bases[1]} (To go back type /back) ")
                val numLayer = readLine()!!
                if (numLayer != "/back") {
                    val parts = numLayer.uppercase().split(".").toList()
                    var result = BasedNumber(parts[0], bases[0]).toBase(bases[1]).number.lowercase()
                    if (parts.size > 1) {
                        result += ".${BasedFraction(parts[1], bases[0]).toBase(bases[1]).number.lowercase()}"
                    }
                    println("Conversion result: $result\n")
                }
            } while (numLayer != "/back")
            println()
        }
    } while (baseLayer != "/exit")
}