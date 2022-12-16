import java.io.File

enum class Ord {
	Right,
	Wrong,
	Same
}

fun main() {

	fun compare(left: Any, right: Any): Ord {

		if (left is Int && right is Int) {
			return when {
				left < right -> Ord.Right
				left > right -> Ord.Wrong
				else -> Ord.Same
			}
		} else {
			val leftList = if (left is List<*>) left as List<Any> else listOf(left as Int)
			val rightList = if (right is List<*>) right as List<Any> else listOf(right as Int)
			val minLen = minOf(leftList.size, rightList.size)
			for (i in 0 until minLen) {
				when (compare(leftList[i], rightList[i])) {
					Ord.Right -> return Ord.Right
					Ord.Wrong -> return Ord.Wrong
					else -> continue
				}
			}

			return when {
				leftList.size < rightList.size -> Ord.Right
				leftList.size > rightList.size -> Ord.Wrong
				else -> Ord.Same
			}

		}
	}

	fun parseList(string: String): List<Any> {
		var string = string.substring(1, string.length - 1)

		fun next(): String {
			var brackets = 0
			val sub = string.takeWhile {
				if (it == '[') brackets++
				else if (it == ']') brackets--

				!(it == ',' && brackets == 0)
			}
			string = string.removePrefix(sub).trimStart(',')

			return sub
		}

		val list = mutableListOf<Any>()

		while (string.isNotBlank()) {
			val n = next()
			if (n.matches("\\d+".toRegex())) list.add(n.toInt())
			else list.add(parseList(n))
		}

		return list
	}

	val lines = File("input.txt").readLines()
	val iterator = lines.iterator()

	var i = 0
	var sum = 0
	do {
		i++
		val a = iterator.next()
		val b = iterator.next()

		if (compare(parseList(a), parseList(b)) == Ord.Right) {

			sum += i
		}

	} while (iterator.hasNext() && iterator.next().isBlank())

	println(sum)

}