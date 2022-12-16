import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	val matrix = lines.toMatrix()

	fun List<List<Char>>.score(x: Int, y: Int): Int {
		val v = this[y][x].digitToInt()
		val rows = this.size
		val columns = this[0].size

		val trees = mutableListOf(this[y].take(x).reversed())
		trees.add(this[y].takeLast(columns - x - 1))

		val vert = this.flatMap { it.filterIndexed { i, _ -> i == x } }
		trees.add(vert.take(y).reversed())
		trees.add(vert.takeLast(rows - y - 1))

		val edge = (x == 0 || x == columns - 1 || y == 0 || y == columns - 1)

		val score = trees.map {
			val init = it.size
			val lim = it.takeWhile { va -> va.digitToInt() < v }.size
			var am = lim
			if (lim < init)
				am++
			am
		}.run {
			this
		}.fold(1) { s, i -> s * i }


		return score

	}

	var max = 0
	matrix.forEachIndexed { y, rows ->
		rows.forEachIndexed { x, _ ->
			max = maxOf(max, matrix.score(x, y))
		}
	}


	println(max)
}