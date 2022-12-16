import java.io.File
import kotlin.math.absoluteValue

fun main() {

	val lines = File("input.txt").readLines()

	var hx = 0
	var hy = 0
	var tx = 0
	var ty = 0

	val tailPrev = mutableSetOf<Pair<Int, Int>>()

	fun moveTail() {
		if ((tx - hx).absoluteValue > 1 || (ty - hy).absoluteValue > 1) {
			if (hx > tx) tx++
			else if (tx > hx) tx--
			if (hy > ty) ty++
			else if (ty > hy) ty--
		}
		tailPrev.add(Pair(tx, ty))
	}

	lines.forEach { l ->
		val dir = l.split(" ")[0]
		val am = l.split(" ", limit = 2)[1].toInt()

		repeat(am) {
			when (dir) {
				"R" -> {
					hx++
				}

				"L" -> {
					hx--
				}

				"U" -> {
					hy++
				}

				"D" -> {
					hy--
				}
			}
			moveTail()
			println("$tx | $ty")
		}

	}

	println(tailPrev.size)
}