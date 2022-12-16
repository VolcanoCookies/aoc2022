import java.io.File

class Room(
	val name: String,
	val flow: Int,
	var open: Boolean,
	val neighbours: MutableList<Room> = mutableListOf(),
	val dist: MutableMap<Room, Int> = mutableMapOf()
) {

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Room) return false

		if (name != other.name) return false

		return true
	}

	override fun hashCode(): Int {
		return name.hashCode()
	}
}

fun main() {

	val lines = File("input.txt").readLines()

	val startAndRooms =
		lines.map { it.getRegexGroups("Valve ([A-z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([A-z, ]+)".toRegex()) }
			.filter { it.isNotEmpty() }
			.map {
				Pair(
					Room(it[0], it[1].toInt(), false),
					it[2].split(",", " ").map { s -> s.trim() }.filter { s -> s.isNotEmpty() })
			}
			.associateBy { it.first.name }
			.run {
				val withNeighbours = this.map { e ->
					val room = e.value.first
					for (ks in e.value.second) {
						val neighbour = this[ks]!!.first
						room.neighbours.add(neighbour)
						room.dist[neighbour] = 1
					}
					room
				}
				withNeighbours.forEach {
					var list = it.neighbours.toMutableList()
					var dist = 1
					while (list.isNotEmpty()) {

						for (room in list) {
							it.dist.putIfAbsent(room, dist)
						}

						dist++
						list =
							list.flatMap { n -> n.neighbours }.filter { n -> !it.dist.keys.contains(n) }.toMutableList()
					}
				}

				withNeighbours
			}
			.run {
				val toRemove = this.filter { it.flow == 0 }
				this.forEach {
					it.neighbours.removeAll(toRemove)
					toRemove.forEach { r ->
						it.dist.remove(r)
					}
					it.dist.remove(it)
				}
				Pair(this.find { it.name == "AA" }!!, this.filter { it.flow > 0 })
			}

	val start = startAndRooms.first
	val rooms = startAndRooms.second

	fun traverse(current: Room, left: List<Room>, released: Int, timeLeft: Int): Int {
		if (timeLeft == 0) return released
		else if (left.isEmpty()) return released + (current.flow * (timeLeft - 1))

		var max = -1
		for (room in left) {
			val dist = current.dist[room]!!
			if (timeLeft >= dist) {
				val v = if (current.flow > 0) {
					traverse(room, left - room, released + (current.flow * (timeLeft - 1)), timeLeft - dist - 1)
				} else {
					traverse(room, left - room, released, timeLeft - dist)
				}
				max = maxOf(max, v)
			} else {
				val v = traverse(current, emptyList(), released, timeLeft)
				max = maxOf(max, v)
			}
		}
		return max
	}

	for (room in rooms) {
		println(room.name)
		println(room.dist.mapKeys { it.key.name })
	}

	val maxPres = traverse(start, rooms, 0, 30)
	println(maxPres)
}