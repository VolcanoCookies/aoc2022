import java.io.File

fun main() {

	val lines = File("input.txt").readLines()
	val priorities = lines.map {
		it.toCharArray().map { c ->
			if (97 <= c.code) {
				c.code - 96
			} else {
				c.code - 64 + 26
			}
		}
	}

	val compartments = priorities.map {
		val len = it.size
		val left = it.take(len / 2)
		val right = it.takeLast(len / 2)
		Pair(left, right)
	}

	val sum = compartments.map {
		it.first.intersect(it.second).sum()
	}.sum()

	println(sum)

}