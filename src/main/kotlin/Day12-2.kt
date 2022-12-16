import java.io.File
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

fun main() {

	val start = measureTimeMillis {
		val adj = listOf(
			Pair(1, 0),
			Pair(-1, 0),
			Pair(0, 1),
			Pair(0, -1)
		)

		operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
			return Pair(this.first + other.first, this.second + other.second)
		}

		fun Pair<Int, Int>.dist(other: Pair<Int, Int>): Pair<Int, Int> {
			return Pair((this.first - other.first).absoluteValue, (this.second - other.second).absoluteValue)
		}

		fun Pair<Int, Int>.length(): Int {
			return this.first + this.second
		}

		val lines = File("input.txt").readLines()

		val matrix = lines.map { it.toCharArray().toList() }.toMatrix()

		val start = matrix.raw.filterValues { it == 'S' }.keys.first()
		val end = matrix.raw.filterValues { it == 'E' }.keys.first()

		matrix.raw[end] = 'z'
		matrix.raw[start] = 'a'

		var shortest = Int.MAX_VALUE

		fun visualize(visited: List<Pair<Int, Int>>) {
			val out = lines.toMutableList().map { it.toCharArray() }
			for (pair in visited) {
				out[pair.second][pair.first] = '#'
			}
			out[end.second][end.first] = 'E'
			out[start.second][start.first] = 'S'

			println("--------------------------------------------------------------------")
			for (s in out.map { it.concatToString() }) {
				println(s)
			}
		}

		val ttp = matrix.raw.mapValues { Int.MAX_VALUE }.toMutableMap()

		fun move(visited: MutableList<Pair<Int, Int>>, length: Int, pos: Pair<Int, Int>, elev: Char): List<Int> {
			if (length > shortest) {
				return emptyList()
			}
			if (ttp[pos]!! <= length) {
				return emptyList()
			} else {
				ttp[pos] = length
			}
			val newVisited = visited.toMutableList()
			newVisited.add(pos)
			if (pos == end) {
				shortest = minOf(shortest, length)
				return listOf(length)
			}
			val possible = adj.map { pos + it }.filter { !newVisited.contains(it) }.mapNotNull {
				val ele = matrix.raw[it]
				if (ele != null) {
					Pair(it, ele)
				} else {
					null
				}
			}
			return possible.filter { it.second - 1 <= elev }
				.sortedBy { it.first.dist(end).length() }
				.sortedByDescending { it.second }
				.flatMap {
					move(newVisited, length + 1, it.first, it.second)
				}
		}

		val paths = matrix.raw.filter { it.value == 'a' }.flatMap {
			move(mutableListOf(), 0, it.key, 'a')
		}



		println(paths.min())
	}
	println(start)

}