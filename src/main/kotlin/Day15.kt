import java.io.File
import kotlin.math.absoluteValue

fun main() {

	fun Pair<Int, Int>.dist(other: Pair<Int, Int>): Int {
		return (this.first - other.first).absoluteValue + (this.second - other.second).absoluteValue
	}

	val lines = File("input.txt").readLines()

	val mapped =
		lines.map { it.split(":", ",").map { it.trim { it.toString().matches("[A-z= ]+".toRegex()) } } }
			.map { Pair(Pair(it[0].toInt(), it[1].toInt()), Pair(it[2].toInt(), it[3].toInt())) }

	val dists = mapped.map {
		Pair(
			it.first,
			it.first.dist(it.second)
		)
	}

	val extremes = dists.flatMap {
		listOf(
			Pair(it.first.first - it.second - 1, it.first.second - it.second - 1),
			Pair(it.first.first + it.second + 1, it.first.second + it.second + 1)
		)
	}

	val minMaxX = extremes.map { it.first }.run { Pair(this.min(), this.max()) }
	val minMaxY = extremes.map { it.second }.run { Pair(this.min(), this.max()) }

	println(minMaxX)
	println(minMaxY)

	val y = 2000000
	var empty = 0
	for (x in -10000000..10000000) {
		val pos = Pair(x, y)
		if (mapped.any { it.second == pos }) {
			continue
		} else if (dists.any { it.first.dist(pos) <= it.second }) {
			empty++
		}
	}
	4856425
	4406514
	4561614
	println(empty)
}