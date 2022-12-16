fun List<String>.toMatrix(): List<List<Char>> {
	return this.map { it.toCharArray().toList() }
}

fun List<List<Char>>.forEach(action: (Int, Int, Char) -> Unit) {
	this.forEachIndexed { y, chars -> chars.forEachIndexed { x, c -> action.invoke(x, y, c) } }
}

fun <T> List<List<T>>.toMatrix(): Matrix<T> {
	val map = this.flatMapIndexed { y: Int, ts: List<T> ->
		ts.mapIndexed { x, t -> Triple(x, y, t) }
	}.fold(HashMap<Pair<Int, Int>, T>()) { map, tri ->
		map[Pair(tri.first, tri.second)] = tri.third
		map
	}
	return Matrix(map)
}

fun String.getRegexGroups(regex: Regex): List<String> {
	return regex.findAll(this).flatMap {
		it.groupValues.run {
			if (this.size > 1) this.subList(1, this.size)
			else emptyList()
		}
	}.toList()
}

class Matrix<T>(
	val raw: HashMap<Pair<Int, Int>, T>
) {

	fun getColumn(y: Int): List<T> {
		return raw.filter { it.key.second == y }.entries.sortedBy { it.key.first }.map { it.value }
	}

	fun getRow(x: Int): List<T> {
		return raw.filter { it.key.first == x }.entries.sortedBy { it.key.second }.map { it.value }
	}

}