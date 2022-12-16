import java.io.File
import kotlin.math.absoluteValue

fun main() {

	val lines = File("input.txt").readLines()

	var screen = ""

	var signal = 1
	var clock = 0

	fun tick() {
		if (((clock % 40) - signal).absoluteValue < 2) {
			screen += "#"
		} else {
			screen += "."
		}

		clock++
		if (clock % 40 == 0) {
			screen += "\n"
		}

	}

	for (line in lines) {
		if (line.startsWith("addx")) {
			val am = line.split(" ")[1].toInt()
			tick()
			tick()
			signal += am
		} else if (line.startsWith("noop")) {
			tick()
		}
	}

	println(screen)

}