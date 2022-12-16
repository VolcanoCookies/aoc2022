import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	fun win(c: Char): Char {
		return when (c) {
			'A' -> {
				'Y'
			}

			'B' -> {
				'Z'
			}

			else -> {
				'X'
			}
		}
	}

	fun draw(c: Char): Char {
		return when (c) {
			'A' -> {
				'X'
			}

			'B' -> {
				'Y'
			}

			else -> {
				'Z'
			}
		}
	}

	fun lose(c: Char): Char {
		return when (c) {
			'A' -> {
				'Z'
			}

			'B' -> {
				'X'
			}

			else -> {
				'Y'
			}
		}
	}

	fun point(c: Char): Int {
		return when (c) {
			'X' -> {
				1
			}

			'Y' -> {
				2
			}

			else -> {
				3
			}
		}
	}

	val res = lines.map {
		it.split(" ", limit = 2)
	}.map {
		val opp = it[0].toCharArray().first()
		val self = it[1].toCharArray().first()

		return@map when (self) {
			'X' -> {
				point(lose(opp))
			}

			'Y' -> {
				point(draw(opp)) + 3
			}

			else -> {
				point(win(opp)) + 6
			}
		}

	}.sum()

	println(res)

}