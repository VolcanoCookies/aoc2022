import java.io.File

fun main() {

	class Monkey(
		var items: ArrayDeque<Int>,
		val operation: (Int) -> Int,
		val test: (Int) -> Boolean,
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
		val starting = iterator.next().split(":")[1].split(",").map { it.trim().toInt() }
		val operation = run {
			val regex = Regex(".*= (\\d+|old) (.) (\\d+|old)")
			val match = regex.matchEntire(iterator.next())!!
			val a = match.groupValues[1]
			val op = match.groupValues[2]
			val b = match.groupValues[3]

			{ i: Int ->
				val aVal = if (a == "old") {
					i
				} else {
					a.toInt()
				}
				val bVal = if (b == "old") {
					i
				} else {
					b.toInt()
				}

				if (op == "+") {
					aVal + bVal
				} else {
					aVal * bVal
				}
			}
		}

		val testDiv = iterator.next().split("by")[1].trim().toInt()
		val test = { i: Int ->
			i % testDiv == 0
		}

		val trueDest = iterator.next().split("monkey")[1].trim().toInt()
		val falseDest = iterator.next().split("monkey")[1].trim().toInt()
		if (iterator.hasNext()) iterator.next()

		monkeys.add(Monkey(ArrayDeque(starting), operation, test, trueDest, falseDest))
	}

	fun reduceWorry(i: Int): Int {
		return i.floorDiv(3)
	}

	fun Monkey.doTurn() {
		for (item in this.items) {
			val worry = reduceWorry(operation.invoke(item))
			if (test.invoke(worry)) {
				monkeys[trueDest].items.addLast(worry)
			} else {
				monkeys[falseDest].items.addLast(worry)
			}
		}
		this.inspections += items.size
		items.clear()
	}

	repeat(20) {
		monkeys.forEach { it.doTurn() }
	}

	for (monkey in monkeys) {
		println(monkey)
	}

	monkeys.sortBy { -it.inspections }
	val monkeyBusiness = monkeys[0].inspections * monkeys[1].inspections
	println(monkeyBusiness)

}