package watermark

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.NumberFormatException
import javax.imageio.ImageIO

const val POS_METHOD_SINGLE = "single"
const val POS_METHOD_GRID = "grid"

fun getFile(name: String): File? {
    println("Input the $name filename:")
    val filename = readLine()!!
    val fin = File(filename)
    if (!fin.isFile) {
        print("The file $filename doesn't exist.")
        return null
    }

    return fin
}

fun readImage(fin: File): BufferedImage {
    return ImageIO.read(fin)
}

fun checkImageCompatibility(image: BufferedImage, name: String): Boolean {
    if (image.colorModel.numColorComponents != 3) {
        print("The number of $name color components isn't 3.")
        return false
    }
    if (image.colorModel.pixelSize !in listOf(24, 32)) {
        print("The $name isn't 24 or 32-bit.")
        return false
    }

    return true
}

fun checkDimensions(base: BufferedImage, watermark: BufferedImage): Boolean {
    return base.width >= watermark.width &&
            base.height >= watermark.height
}

fun badColor(): Color? {
    print("The transparency color input is invalid.")
    return null
}

fun askAboutTransparentColor(): Color? {
    val colorRegex = Regex("^(\\d{1,3}) (\\d{1,3}) (\\d{1,3})\$")

    println("Input a transparency color ([Red] [Green] [Blue]):")
    val answer = readLine()!!
    val match = colorRegex.find(answer) ?: return badColor()

    val color = MutableList(4) { 0 }
    for (i in 1..3) {
        color[i] = match.groupValues[i].toInt()
        if (color[i] !in 0..255) {
            return badColor()
        }
    }

    return Color(color[1], color[2], color[3])
}

fun askAboutTransparency(image: BufferedImage): Boolean {
    println(
        if (image.transparency == 3) "Do you want to use the watermark's Alpha channel?"
        else "Do you want to set a transparency color?"
    )
    val answer = readLine()!!.lowercase()

    return answer == "yes"
}

fun getWatermarkWeight(): Int? {
    println("Input the watermark transparency percentage (Integer 0-100):")
    val watermarkWeight: Int
    try {
        watermarkWeight = readLine()!!.toInt()
        if (watermarkWeight !in 0..100) {
            print("The transparency percentage is out of range.")
            return null
        }
    } catch (e: NumberFormatException) {
        print("The transparency percentage isn't an integer number.")
        return null
    }

    return watermarkWeight
}

fun getPosMethod(): String? {
    println("Choose the position method (single, grid):")
    val answer = readLine()!!

    return if (answer !in listOf(POS_METHOD_SINGLE, POS_METHOD_GRID)) {
        print("The position method input is invalid.")
        null
    } else answer
}

fun getCoordsDiff(base: BufferedImage, watermark: BufferedImage): List<Int> {
    return listOf(
        base.width - watermark.width,
        base.height - watermark.height
    )
}

fun invalidCoordsInput(): List<Int>? {
    print("The position input is invalid.")
    return null
}

fun getPosCoords(coordsDiff: List<Int>): List<Int>? {
    val coordsRegex = Regex("^(-?\\d+) (-?\\d+)\$")

    println("Input the watermark position ([x 0-${coordsDiff[0]}] [y 0-${coordsDiff[1]}]):")
    val answer = readLine()!!
    val match = coordsRegex.find(answer) ?: return invalidCoordsInput()

    val posCoords = MutableList(3) { 0 }
    for (i in 1..2) {
        posCoords[i] = match.groupValues[i].toInt()
        if (posCoords[i] !in 0..coordsDiff[i - 1]) {
            print("The position input is out of range.")
            return null
        }
    }

    return posCoords
}

fun getOutputFile(): File? {
    val formatRegex = Regex(".+\\.(?:jpg|png)")

    println("Input the output image filename (jpg or png extension):")
    val filename = readLine()!!

    if (!filename.matches(formatRegex)) {
        print("The output file extension isn't \"jpg\" or \"png\".")
        return null
    }

    return File(filename)
}

fun combineComponents(i: Int, w: Int, weight: Int): Int {
    return (weight * w + (100 - weight) * i) / 100
}

fun putWatermark(
    image: BufferedImage,
    watermark: BufferedImage,
    weight: Int,
    modifyTransparency: Boolean,
    transparentColor: Color?,
    posCoords: List<Int>?
): BufferedImage {
    for (height in 0 until image.height) for (width in 0 until image.width) {
        if (
            posCoords != null && (
                    width !in posCoords[1] until (watermark.width + posCoords[1]) ||
                            height !in posCoords[2] until (watermark.height + posCoords[2])
                    )
        ) {
            continue
        }

        var watermarkPixel = Color(
            if (posCoords == null) {
                watermark.getRGB(
                    width % watermark.width,
                    height % watermark.height
                )
            } else {
                watermark.getRGB(
                    width - posCoords[1],
                    height - posCoords[2]
                )
            }, true
        )

        if (transparentColor != null &&
            watermarkPixel.red == transparentColor.red &&
            watermarkPixel.green == transparentColor.green &&
            watermarkPixel.blue == transparentColor.blue
        ) {
            watermarkPixel = Color(0, 0, 0, 0)
        }
        val waterAlpha = if (modifyTransparency) watermarkPixel.alpha else 255
        if (waterAlpha == 255) {
            val imagePixel = Color(image.getRGB(width, height), true)

            image.setRGB(
                width, height,
                Color(
                    combineComponents(imagePixel.red, watermarkPixel.red, weight),
                    combineComponents(imagePixel.green, watermarkPixel.green, weight),
                    combineComponents(imagePixel.blue, watermarkPixel.blue, weight)
                ).rgb
            )
        }
    }

    return image
}

fun getFormat(file: File): String {
    val formatRegex = Regex(".+\\.(.+)")
    val match = formatRegex.find(file.path)!!

    return match.groupValues[1]
}

fun saveImage(file: File, image: BufferedImage) {
    val noAlphaImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)
    for (height in 0 until image.height) for (width in 0 until image.width) {
        noAlphaImage.setRGB(width, height, image.getRGB(width, height))
    }

    ImageIO.write(noAlphaImage, getFormat(file), file)
    print("The watermarked image ${file.path} has been created.")
}

fun main() {
    val baseFile = getFile("image") ?: return
    val baseImage = readImage(baseFile)
    if (!checkImageCompatibility(baseImage, "image")) {
        return
    }

    val watermarkFile = getFile("watermark image") ?: return
    val watermarkImage = readImage(watermarkFile)
    if (!checkImageCompatibility(watermarkImage, "watermark")) {
        return
    }
    if (!checkDimensions(baseImage, watermarkImage)) {
        print("The watermark's dimensions are larger.")
        return
    }
    val modifyTransparency = askAboutTransparency(watermarkImage)
    val transparentColor = if (watermarkImage.transparency != 3 && modifyTransparency) {
        askAboutTransparentColor() ?: return
    } else null

    val watermarkWeight = getWatermarkWeight() ?: return

    val posCoords = if ((getPosMethod() ?: return) == "single") {
        getPosCoords(getCoordsDiff(baseImage, watermarkImage)) ?: return
    } else null

    val outputFile = getOutputFile() ?: return

    val resultImage = putWatermark(
        baseImage,
        watermarkImage,
        watermarkWeight,
        modifyTransparency,
        transparentColor,
        posCoords
    )
    saveImage(outputFile, resultImage)
}