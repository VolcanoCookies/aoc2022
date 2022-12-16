import java.io.File
import kotlin.math.absoluteValue
import kotlin.system.exitProcess

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

	val max = 4000000
	var y = 0
	var x: Int
	do {
		if (y % (max / 1000) == 0)
			println("${y / (max / 1000)}/1000")
		x = 0
		do {
			val pos = Pair(x, y)

			val d = dists.map { Triple(it.first, it.second, it.second - it.first.dist(pos)) }.filter { it.third >= 0 }
			if (d.isEmpty()) {
				println(pos.first.toBigInteger().multiply(max.toBigInteger()).add(pos.second.toBigInteger()).toString())
				exitProcess(0)
			}
			val m = d.maxBy { it.third }

			x += m.third

		} while (++x <= max)
	} while (++y <= max)

}