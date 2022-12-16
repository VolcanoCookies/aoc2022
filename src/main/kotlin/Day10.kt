import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	var signal = 1
	var clock = 0

	var sum = 0

	fun tick() {
		clock++

		if ((clock + 20) % 40 == 0) {
			sum += signal * clock
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

	println(sum)

}