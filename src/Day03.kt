fun main() = Day03.run(RunMode.BOTH)

object Day03 : BasicDay() {
    override val expectedTestValuePart1 = 4
    override val expectedTestValuePart2 = 3

    override val solvePart1: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        val moves = input.first().map { charToDirection(it) }
        val startPosition = Vector2(0, 0)

        val visitedHouses = deliverPackages(moves, startPosition)

        visitedHouses.count()
    }

    override val solvePart2: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        val (santaMoves, roboSantaMoves) =
            input
                .first()
                .map { charToDirection(it) }
                .windowed(2, 2)
                .map { (santaMove, roboSantaMove) -> santaMove to roboSantaMove }
                .unzip()

        val santaStartPosition = Vector2(0, 0)
        val roboSantaStartPosition = Vector2(0, 0)

        val visitedHousesSanta = deliverPackages(santaMoves, santaStartPosition)
        val visitedHousesRoboSanta = deliverPackages(roboSantaMoves, roboSantaStartPosition)

        (visitedHousesSanta + visitedHousesRoboSanta).count()
    }

    private data class Vector2(
        val x: Int,
        val y: Int,
    ) {
        operator fun plus(other: Vector2) = Vector2(this.x + other.x, this.y + other.y)
    }

    private enum class Direction(
        val vector2: Vector2,
    ) {
        NORTH(Vector2(0, -1)),
        SOUTH(Vector2(0, 1)),
        EAST(Vector2(1, 0)),
        WEST(Vector2(-1, 0)),
    }

    private fun charToDirection(char: Char) =
        when (char) {
            '^' -> Direction.NORTH
            'v' -> Direction.SOUTH
            '>' -> Direction.EAST
            '<' -> Direction.WEST

            else -> error("Could not parse char $char to a direction.")
        }

    private fun deliverPackages(
        moves: List<Direction>,
        startPosition: Vector2,
    ): Set<Vector2> {
        val visitedHouses = mutableSetOf(startPosition)
        var currentPosition = startPosition

        moves.forEach {
            currentPosition += it.vector2
            visitedHouses += currentPosition
        }

        return visitedHouses
    }
}
