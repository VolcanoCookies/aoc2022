import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	val matrix = lines.toMatrix()

	fun List<List<Char>>.visible(x: Int, y: Int): Boolean {
		val v = this[y][x].digitToInt()
		val rows = this.size
		val columns = this[0].size

		if (x == 0 || x == columns - 1 || y == 0 || y == columns - 1)
			return true

		var visible = true
		for (dy in 0 until y) {
			if (this[dy][x].digitToInt() >= v) {
				visible = false
				break
			}
		}
		if (visible) return true
		visible = true
		for (dy in y + 1 until rows) {
			if (this[dy][x].digitToInt() >= v) {
				visible = false
				break
			}
		}
		if (visible) return true
		visible = true
		for (dx in 0 until x) {
			if (this[y][dx].digitToInt() >= v) {
				visible = false
				break
			}
		}
		if (visible) return true
		visible = true
		for (dx in x + 1 until columns) {
			if (this[y][dx].digitToInt() >= v) {
				visible = false
				break
			}
		}
		return visible
	}

	var visible = 0
	matrix.forEachIndexed { y, rows ->
		rows.forEachIndexed { x, c ->
			if (matrix.visible(x, y)) {
				visible++
			}
		}
	}


	println(visible)
}