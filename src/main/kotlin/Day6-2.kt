import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	val chars = lines[0].toCharArray()

	fun ArrayDeque<Char>.unique(): Boolean {
		return this.size == 14 && this.toSet().size == this.size
	}

	val last = ArrayDeque<Char>()
	var i = 0
	for (c in chars) {
		i++
		last.addFirst(c)
		if (last.size > 14) {
			last.removeLast()

			if (last.unique()) {
				println(i)
				break
			}
		}

	}

}