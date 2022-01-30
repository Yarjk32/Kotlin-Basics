fun main() {
    var a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    val n = readLine()!!.toInt()

    /**
    Examples:
    ⠀a -->a  (b - a) / n    b
    ⠀| -->|-----|-----|-----| +1 (if expression)
    -9   -5     0     5    10  result 4

    ⠀a>a b
    ⠀|-|-| will print 1 because of the if expression
    -1 0 1

    ⠀a   b
    ⠀|---| will do nothing to a, print 0
    ⠀1   4
     **/

    // putting var. a to the closest divisible number
    // but only if it isn't div.
    // and if it will not be bigger than b
    if (a % n != 0) a = Math.min(a + n - Math.abs(a.mod(n)), b)

    // (b - a) / n – all divisible numbers within (a;b]
    // if expression – adds +1 for divisible a
    print((b - a) / n + if (a % n == 0) 1 else 0)
}
