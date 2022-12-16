import java.io.File
import kotlin.math.absoluteValue

class Knot(
	var x: Int,
	var y: Int
) {

	var prevPos = mutableSetOf<Pair<Int, Int>>()

	fun follow(other: Knot) {
		if ((this.x - other.x).absoluteValue > 1 || (this.y - other.y).absoluteValue > 1) {
			if (other.x > this.x) this.x++
			else if (this.x > other.x) this.x--
			if (other.y > this.y) this.y++
			else if (this.y > other.y) this.y--
		}
		prevPos.add(Pair(this.x, this.y))
	}

}

fun main() {

	val lines = File("input.txt").readLines()

	val head = Knot(0, 0)
	val tails = mutableListOf<Knot>()
	repeat(9) {
		tails.add(Knot(0, 0))
	}

	lines.forEach { l ->
		val dir = l.split(" ")[0]
		val am = l.split(" ", limit = 2)[1].toInt()

		repeat(am) {
			when (dir) {
				"R" -> {
					head.x++
				}

				"L" -> {
					head.x--
				}

				"U" -> {
					head.y++
				}

				"D" -> {
					head.y--
				}
			}

			tails.forEachIndexed { i, k ->
				if (i == 0) {
					k.follow(head)
				} else {
					k.follow(tails[i - 1])
				}
			}
		}

	}

	println(tails.last().prevPos.size)
}