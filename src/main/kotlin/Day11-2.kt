import java.io.File

fun main() {

	class Monkey(
		var items: ArrayDeque<Long>,
		val operation: (Long, Long) -> Long,
		val test: Long,
		val trueDest: Int,
		val falseDest: Int,
		var inspections: Int = 0
	) {

		override fun toString(): String {
			return "Monkey(items=$items, operation=$operation, test=$test, trueDest=$trueDest, falseDest=$falseDest, inspections=$inspections)"
		}
	}

	val lines = File("input.txt").readLines()

	val monkeys = mutableListOf<Monkey>()

	val iterator = lines.listIterator()
	while (iterator.hasNext()) {
		iterator.next()
		val starting = iterator.next().split(":")[1].split(",").map { it.trim().toLong() }
		val operation = run {
			val regex = Regex(".*= (\\d+|old) (.) (\\d+|old)")
			val match = regex.matchEntire(iterator.next())!!
			val a = match.groupValues[1]
			val op = match.groupValues[2]
			val b = match.groupValues[3]

			{ i: Long, m: Long ->
				val aVal = if (a == "old") {
					i
				} else {
					a.toLong()
				}
				val bVal = if (b == "old") {
					i
				} else {
					b.toLong()
				}

				val v = if (op == "+") {
					aVal + bVal
				} else {
					aVal * bVal
				}

				v % m
			}
		}

		val test = iterator.next().split("by")[1].trim().toLong()
		val trueDest = iterator.next().split("monkey")[1].trim().toInt()
		val falseDest = iterator.next().split("monkey")[1].trim().toInt()
		if (iterator.hasNext()) iterator.next()

		monkeys.add(Monkey(ArrayDeque(starting), operation, test, trueDest, falseDest))
	}

	fun reduceWorry(i: Long): Long {
		return i
	}

	val max = monkeys.map { it.test }.fold(1L) { s, i -> s * i }

	fun Monkey.doTurn() {
		for (item in this.items) {
			val worry = reduceWorry(operation.invoke(item, max))
			if (worry % test == 0L) {
				monkeys[trueDest].items.addLast(worry)
			} else {
				monkeys[falseDest].items.addLast(worry)
			}
		}
		this.inspections += items.size
		items.clear()
	}

	repeat(10000) {
		monkeys.forEach { it.doTurn() }
	}

	for (monkey in monkeys) {
		println(monkey)
	}

	monkeys.sortBy { -it.inspections }
	val monkeyBusiness = monkeys[0].inspections.toBigInteger() * monkeys[1].inspections.toBigInteger()
	println(monkeyBusiness)

}