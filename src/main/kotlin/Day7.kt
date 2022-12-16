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

	println(sizes)

	val res = sizes.values.filter { it <= 100000 }.sum()
	println(res)
}