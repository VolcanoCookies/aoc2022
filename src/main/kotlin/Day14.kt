import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.min

enum class Pixel {
	Air,
	Sand,
	Rock
}

fun main() {

	val lines = File("input.txt").readLines()

	val nums =
		lines.flatMap { it.split("[,\\-> ]".toRegex()) }.map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }
	val minX = nums.filter { it > 200 }.min() - 1
	val maxX = nums.max() + 1

	val maxY = nums.filter { it < 200 }.max() + 1

	val plane = mutableListOf<MutableList<Pixel>>()

	repeat(maxY) {
		val list = mutableListOf<Pixel>()
		repeat(maxX - minX) {
			list.add(Pixel.Air)
		}
		plane.add(list)
	}

	for (line in lines) {
		var prevX: Int? = null
		var prevY: Int? = null
		for (s in line.split(" -> ").map { it.split(",") }) {
			val x = s[0].toInt() - minX
			val y = s[1].toInt()
			if (prevX != null && prevY != null) {
				repeat((prevX!! - x).absoluteValue + 1) { dx ->
					plane[y][min(prevX!!, x) + dx] = Pixel.Rock
				}
				repeat((prevY!! - y).absoluteValue + 1) { dy ->
					plane[min(prevY!!, y) + dy][x] = Pixel.Rock
				}
			}
			prevX = x
			prevY = y
		}
	}


	fun printPlane() {
		val e = plane.map {
			it.joinToString("") {
				return@joinToString when (it) {
					Pixel.Air -> "."
					Pixel.Sand -> "o"
					Pixel.Rock -> "#"
				}
			}
		}.joinToString("\n")
		println(e)
		println("---------------------------------------------------------------------------")
	}

	var posX: Int
	var posY = 0
	var sand = 0
	while (posY + 1 != maxY) {
		sand++
		posX = 500 - minX
		posY = 0
		while (true) {
			if (posY + 1 == maxY) break

			if (plane[posY + 1][posX] == Pixel.Air) {
				posY++
			} else if (plane[posY + 1][posX - 1] == Pixel.Air) {
				posY++
				posX--
			} else if (plane[posY + 1][posX + 1] == Pixel.Air) {
				posY++
				posX++
			} else {
				plane[posY][posX] = Pixel.Sand
				break
			}

		}
		//printPlane()
	}


	sand--
	println(sand)

}