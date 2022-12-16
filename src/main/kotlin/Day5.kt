import java.io.File

fun main() {

	val lines = File("input.txt").readLines()

	val inp = lines.filter { it.contains("[") }
		.map { it.chunked(4).map { s -> s.trim(' ', '[', ']') } }

	val stackCount = inp.maxBy { it.size }.size

	var stacks = mutableListOf<MutableList<Char>>()
	repeat(stackCount) {
		stacks.add(mutableListOf())
	}

	inp.fold(stacks) { s, r ->
		r.forEachIndexed { index, str ->
			if (str.isNotBlank()) {
				s[index].add(str.toCharArray()[0])
			}
		}

		s
	}

	val instructions = lines.filter { it.contains("move") }
		.map { it.split(" ") }
		.map { Triple(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1) }

	fun doInstruction(inst: Triple<Int, Int, Int>, stack: MutableList<MutableList<Char>>) {
		repeat(inst.first) {
			val c = stack[inst.second].removeFirst()
			stack[inst.third].add(0, c)
		}
	}

	instructions.forEach { doInstruction(it, stacks) }

	val top = stacks.map { it.first() }.joinToString(separator = "")

	println(top)

}