import java.io.File

fun main() {

	val lines = File("input.txt").readLines()
	val res = lines.map {
		it.split(" ", limit = 2)
	}.map {
		val opp = it[0].toCharArray().first()
		val self = it[1].toCharArray().first()

		val win = (opp == 'A' && self == 'Y') || (opp == 'B' && self == 'Z') || (opp == 'C' && self == 'X')
		val tie = (opp == 'A' && self == 'X') || (opp == 'B' && self == 'Y') || (opp == 'C' && self == 'Z')

		var score = if (win) {
			6
		} else if (tie) {
			3
		} else {
			0
		}

		return@map score + when (self) {
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
	}.sum()

	println(res)

}