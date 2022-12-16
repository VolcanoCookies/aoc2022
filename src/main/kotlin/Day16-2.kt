import java.io.File

fun traverse(current: Room, other: Room, wait: Int, left: List<Room>, released: Int, timeLeft: Int): Int {
	if (0 >= timeLeft) return released

	val newReleased = released + (current.flow * (timeLeft - 1))

	val waitLeft = wait - 1
	val newTimeLeft = timeLeft - 1

	if (left.isEmpty()) {
		val v = if (newTimeLeft > waitLeft) {
			other.flow * (newTimeLeft - waitLeft - 1)
		} else {
			0
		}
		return newReleased + v
	}

	var max = -1

	for (next in left) {
		val dist = current.dist[next]!!
		val v = if (dist > waitLeft) {
			traverse(other, next, dist - waitLeft, left - next, newReleased, newTimeLeft - waitLeft)
		} else if (waitLeft > dist) {
			traverse(next, other, waitLeft - dist, left - next, newReleased, newTimeLeft - dist)
		} else {
			traverseSim(next, other, left - next, newReleased, newTimeLeft - dist)
		}

		max = maxOf(max, v)
	}

	return max
}

fun traverseSim(room1: Room, room2: Room, left: List<Room>, released: Int, timeLeft: Int): Int {

	if (timeLeft <= 0) return released

	var newReleased = released

	val o1 = room1.flow > 0
	val o2 = room2.flow > 0
	if (o1) {
		newReleased += room1.flow * (timeLeft - 1)
	}
	if (o2) {
		newReleased += room2.flow * (timeLeft - 1)
	}

	if (left.isEmpty()) {
		return newReleased
	}

	var max = -1
	for (next1 in left) {
		val dist1 = room1.dist[next1]!! + if (o1) 1 else 0
		for (next2 in (left - next1)) {
			val dist2 = room2.dist[next2]!! + if (o2) 1 else 0

			val v = if (dist1 > dist2) {
				traverse(next2, next1, dist1 - dist2, left - next1 - next2, newReleased, timeLeft - dist2)
			} else if (dist2 > dist1) {
				traverse(next1, next2, dist2 - dist1, left - next1 - next2, newReleased, timeLeft - dist1)
			} else {
				traverseSim(next1, next2, left - next1 - next2, newReleased, timeLeft - dist1)
			}

			max = maxOf(max, v)
		}
	}

	return max
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

	val maxPres = traverseSim(start, start, rooms, 0, 26)
	println(maxPres)
}