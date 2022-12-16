import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	val allCals = mutableListOf<Int>()
	var cals = 0
	for (line in lines) {
		if (line.isBlank()) {
			allCals.add(cals)
			cals = 0
		} else {
			cals += line.toInt()
		}
	}
	allCals.add(cals)

	var sum = 0
	repeat(3) {
		val v = allCals.max()
		allCals.remove(v)
		sum += v
	}

	println(sum)

}