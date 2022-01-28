fun main() {
    val (box1x, box1y, box1z) = readLine()!!.split(" ")
    val (box2x, box2y, box2z) = readLine()!!.split(" ")
    var x1 = box1x.toInt()
    var y1 = box1y.toInt()
    var z1 = box1z.toInt()
    var x2 = box2x.toInt()
    var y2 = box2y.toInt()
    var z2 = box2z.toInt()
    val swapped = x1 * y1 * z1 < x2 * y2 * z2
    if (swapped) {
        var m = x1
        x1 = x2
        x2 = m
        m = y1
        y1 = y2
        y2 = m
        m = z1
        z1 = z2
        z2 = m
    }
    if (x1 == x2 && y1 == y2 && z1 == z2 || x1 == x2 && y1 == z2 && z1 == y2 || y1 == x2 && z1 == y2 && x1 == z2 || y1 == x2 && z1 == z2 && x1 == y2 || z1 == x2 && x1 == y2 && y1 == z2 || z1 == x2 && x1 == z2 && y1 == y2) print("Box 1 = Box 2")
    else if (x1 >= x2 && y1 >= y2 && z1 >= z2 || x1 >= x2 && y1 >= z2 && z1 >= y2 || y1 >= x2 && z1 >= y2 && x1 >= z2 || y1 >= x2 && z1 >= z2 && x1 >= y2 || z1 >= x2 && x1 >= y2 && y1 >= z2 || z1 >= x2 && x1 >= z2 && y1 >= y2) print("Box 1 ${if (swapped) "<" else ">"} Box 2")
    else print("Incomparable")
}