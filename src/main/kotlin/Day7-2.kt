import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	val loc = ArrayDeque<String>()
	val sizes = HashMap<String, Int>()

	for (line in lines) {
		if (line.startsWith("$ cd")) {
			val dir = line.split(" ", limit = 3)[2]
			if (dir == "..") {
				loc.removeFirst()
			} else {
				loc.addFirst(loc.joinToString("/") + dir)
			}
		}

		if (line.matches("\\d+ .*".toRegex())) {
			val size = line.split(" ")[0].toInt()
			loc.forEach {
				sizes[it] = (sizes[it] ?: 0) + size
			}
		}

	}

	val tot = 70000000
	val req = 30000000

	val used = sizes["/"]!!
	val free = tot - used
	val dif = req - free

	val res = sizes.filter { it.value > dif }.minBy { it.value }

	println(res)
}