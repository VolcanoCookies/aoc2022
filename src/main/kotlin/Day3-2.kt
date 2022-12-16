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

	val groups = priorities.chunked(3)
	val sum = groups.map { g ->
		val t = g.fold(g.first().toSet()) { acc, l ->
			acc.intersect(l.toSet())
		}
		t.first()
	}.sum()

	println(sum)

}