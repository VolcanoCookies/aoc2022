import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	fun IntRange.containsWhole(r: IntRange): Boolean {
		return this.contains(r.first) && this.contains(r.last)
	}

	fun IntRange.containsSome(r: IntRange): Boolean {
		return this.contains(r.first) || this.contains(r.last)
	}

	val ans = lines.map {
		it.split(",", limit = 2).map { s ->
			val l = s.split("-", limit = 2)
			val min = l[0].toInt()
			val max = l[1].toInt()
			min..max
		}
	}.map {
		Pair(it[0], it[1])
	}.filter {
		it.first.containsSome(it.second) || it.second.containsSome(it.first)
	}.count()

	println(ans)
}