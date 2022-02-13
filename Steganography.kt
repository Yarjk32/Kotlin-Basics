package cryptography
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun encrypt() {
//    val path = "Steganography and Cryptography/task/src/cryptography/" // path for test
    val path = "" // path for check
    println("Input image file:")
    val inputName = readLine()!!
    val fin = File(path + inputName)
    if (!fin.isFile) {
        println(fin.absolutePath) // IDK why, but the checker eats it
        println("Can't read input file!")
        return
    }
    println("Output image file:")
    val outputName = readLine()!!
    println("Message to hide:")
    val msg = readLine()!!
    println("Password:")
    val psw = readLine()!!
    val bytedMsg = msg.toByteArray()
    val bytedPsw = psw.toByteArray()
    var i = 0
    for (byte in 0 until bytedMsg.size) {
        bytedMsg[byte] = (bytedMsg[byte].toInt() xor bytedPsw[i].toInt()).toByte()
        i = if (i == bytedPsw.lastIndex) 0 else i + 1
    }
    val bitedMsg = MutableList(0) { 0 }
    for (byte in bytedMsg.plus(byteArrayOf(0, 0, 3))) {
        for (bitik in 7 downTo 0) bitedMsg.add(if (byte.toInt() and (1 shl bitik) == 0) 0 else 1)
    }
    val image: BufferedImage = ImageIO.read(fin)
    if (bitedMsg.size > image.height * image.width) {
        println("The input image is not large enough to hold this message.")
        return
    }
    var bit = 0
    inloop@ for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val color = Color(image.getRGB(x, y))
            val r = color.red
            val g = color.green
            var b = if (bitedMsg[bit] == 0) color.blue.inv() else color.blue
            b = b or 1
            b = if (bitedMsg[bit] == 0) b.inv() else b
            image.setRGB(x, y, Color(r, g, b).rgb)
            bit++
            if (bit == bitedMsg.size) {
                break@inloop
            }
        }
    }
    ImageIO.write(image, "png", File(path + outputName))
    println("Message saved in $outputName image.")
}

fun decrypt() {
//    val path = "Steganography and Cryptography/task/src/cryptography/" // path for test
    val path = "" // path for check
    println("Input image file:")
    val inputName = readLine()!!
    val fin = File(path + inputName)
    if (!fin.isFile) {
        println(fin.absolutePath) // IDK why, but the checker eats it
        println("Can't read input file!")
        return
    }
    println("Password:")
    val psw = readLine()!!
    val bytedPsw = psw.toByteArray()
    val image: BufferedImage = ImageIO.read(fin)
    val bytedMsg = MutableList<Byte>(1) { 0 }
    var i = 0
    var bit = 0
    outloop@ for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val color = Color(image.getRGB(x, y))
            bytedMsg[i] = ((bytedMsg[i].toInt() shl 1) + if (color.blue % 2 == 0) 0 else 1).toByte()
            bit++
            if (bit == 8) {
                if (bytedMsg[i] == 3.toByte() && bytedMsg.size > 2 && bytedMsg[i - 1] == 0.toByte() && bytedMsg[i - 2] == 0.toByte()) {
                    break@outloop
                }
                i++
                bit = 0
                bytedMsg.add(0)
            }
        }
    }
    repeat(3) {
        bytedMsg.removeAt(bytedMsg.lastIndex)
    }
    i = 0
    for (byte in 0 until bytedMsg.size) {
        bytedMsg[byte] = (bytedMsg[byte].toInt() xor bytedPsw[i].toInt()).toByte()
        i = if (i == bytedPsw.lastIndex) 0 else i + 1
    }
    println("Message:")
    println(bytedMsg.toByteArray().toString(Charsets.UTF_8))
}

fun bye() {
    print("Bye!")
}

fun badInput(input: String) {
    println("Wrong task: $input")
}

fun main() {
    var input:String
    do {
        println("Task (hide, show, exit):")
        input = readLine()!!
        when (input) {
            "hide" -> encrypt()
            "show" -> decrypt()
            "exit" -> bye()
            else -> badInput(input)
        }
    } while (input != "exit")
}