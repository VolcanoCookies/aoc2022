import java.io.File

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

	fun Ord.toInt(): Int {
		return when (this) {
			Ord.Right -> -1
			Ord.Wrong -> 1
			Ord.Same -> 0
		}
	}

	val lines = File("input.txt").readLines()

	val a = parseList("[[2]]")
	val b = parseList("[[6]]")

	val sorted = lines.filter { it.isNotBlank() }.map { parseList(it) }.run {
		this + listOf(a, b)
	}.sortedWith { l, r -> compare(l, r).toInt() }

	val ai = sorted.indexOf(a) + 1
	val bi = sorted.indexOf(b) + 1

	println(ai * bi)

}