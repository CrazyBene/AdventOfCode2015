fun main() = Day01.run(RunMode.BOTH)

object Day01 : BasicDay(separateTestFiles = true) {
    override val expectedTestValuePart1 = 3
    override val expectedTestValuePart2 = 5

    override val solvePart1: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        input
            .first()
            .map {
                when (charToElevatorDirection(it)) {
                    ElevatorDirection.UP -> 1
                    ElevatorDirection.DOWN -> -1
                }
            }.sum()
    }

    override val solvePart2: ((input: List<String>, isTestRun: Boolean) -> Int) = solve@{ input, _ ->
        input.first().foldIndexed(0) { index, currentFloor, char ->
            val nextFloor =
                currentFloor +
                    when (charToElevatorDirection(char)) {
                        ElevatorDirection.UP -> 1
                        ElevatorDirection.DOWN -> -1
                    }

            if (nextFloor < 0) return@solve index + 1

            nextFloor
        }

        error("Never entered the basement.")
    }

    private enum class ElevatorDirection {
        UP,
        DOWN,
    }

    private fun charToElevatorDirection(char: Char): ElevatorDirection =
        when (char) {
            '(' -> ElevatorDirection.UP
            ')' -> ElevatorDirection.DOWN
            else -> error("Unknown symbol $char.")
        }
}
