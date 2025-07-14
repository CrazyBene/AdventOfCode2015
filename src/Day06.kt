import kotlin.math.max

fun main() = Day06.run(RunMode.BOTH)

object Day06 : BasicDay(separateTestFiles = true) {
    override val expectedTestValuePart1 = 998996
    override val expectedTestValuePart2 = 2000001

    override val solvePart1: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        val instructions = Instruction.parse(input)

        val lightGrid = LightGrid(1000, 1000) { false }

        instructions.forEach { instruction ->
            instruction.runV1(lightGrid)
        }

        lightGrid.lights.flatten().count { it }
    }

    override val solvePart2: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        val instructions = Instruction.parse(input)

        val lightGrid = LightGrid(1000, 1000) { 0 }

        instructions.forEach { instruction ->
            instruction.runV2(lightGrid)
        }

        lightGrid.lights.flatten().sum()
    }

    private data class Position(
        val x: Int,
        val y: Int,
    )

    private class LightGrid<T>(
        width: Int,
        height: Int,
        default: () -> T,
    ) {
        val lights =
            MutableList(height) {
                MutableList(width) {
                    default()
                }
            }

        fun setLight(
            position: Position,
            value: T,
        ) {
            lights[position.y][position.x] = value
        }

        fun getLight(position: Position) = lights[position.y][position.x]
    }

    private fun LightGrid<Int>.increaseLight(
        position: Position,
        amount: Int,
    ) {
        this.lights[position.y][position.x] = max(0, this.lights[position.y][position.x] + amount)
    }

    private sealed class Instruction(
        val startPosition: Position,
        val endPosition: Position,
    ) {
        companion object {
            val instructionRegex =
                """(${TurnOnInstruction.INSTRUCTION_STRING}|${TurnOffInstruction.INSTRUCTION_STRING}|${ToggleInstruction.INSTRUCTION_STRING}) (\d+),(\d+) through (\d+),(\d+)"""
                    .toRegex()

            fun parse(lines: List<String>) =
                lines.map { line ->
                    instructionRegex.find(line).let { result ->
                        if (result == null) error("Could not parse line $line.")

                        val startPositon = Position(result.groupValues[2].toInt(), result.groupValues[3].toInt())
                        val endPositon = Position(result.groupValues[4].toInt(), result.groupValues[5].toInt())

                        when (result.groupValues[1]) {
                            TurnOnInstruction.INSTRUCTION_STRING -> TurnOnInstruction(startPositon, endPositon)
                            TurnOffInstruction.INSTRUCTION_STRING -> TurnOffInstruction(startPositon, endPositon)
                            ToggleInstruction.INSTRUCTION_STRING -> ToggleInstruction(startPositon, endPositon)

                            else -> error("Could not parse instruction ${result.groupValues[1]}")
                        }
                    }
                }
        }

        abstract fun runV1(lightGrid: LightGrid<Boolean>)

        abstract fun runV2(lightGrid: LightGrid<Int>)
    }

    private class TurnOnInstruction(
        startPosition: Position,
        endPosition: Position,
    ) : Instruction(startPosition, endPosition) {
        companion object {
            const val INSTRUCTION_STRING = "turn on"
        }

        override fun runV1(lightGrid: LightGrid<Boolean>) {
            for (y in startPosition.y..endPosition.y) {
                for (x in startPosition.x..endPosition.x) {
                    lightGrid.setLight(Position(x, y), true)
                }
            }
        }

        override fun runV2(lightGrid: LightGrid<Int>) {
            for (y in startPosition.y..endPosition.y) {
                for (x in startPosition.x..endPosition.x) {
                    lightGrid.increaseLight(Position(x, y), 1)
                }
            }
        }
    }

    private class TurnOffInstruction(
        startPosition: Position,
        endPosition: Position,
    ) : Instruction(startPosition, endPosition) {
        companion object {
            const val INSTRUCTION_STRING = "turn off"
        }

        override fun runV1(lightGrid: LightGrid<Boolean>) {
            for (y in startPosition.y..endPosition.y) {
                for (x in startPosition.x..endPosition.x) {
                    lightGrid.setLight(Position(x, y), false)
                }
            }
        }

        override fun runV2(lightGrid: LightGrid<Int>) {
            for (y in startPosition.y..endPosition.y) {
                for (x in startPosition.x..endPosition.x) {
                    lightGrid.setLight(Position(x, y), max(0, lightGrid.getLight(Position(x, y)) - 1))
                }
            }
        }
    }

    private class ToggleInstruction(
        startPosition: Position,
        endPosition: Position,
    ) : Instruction(startPosition, endPosition) {
        companion object {
            const val INSTRUCTION_STRING = "toggle"
        }

        override fun runV1(lightGrid: LightGrid<Boolean>) {
            for (y in startPosition.y..endPosition.y) {
                for (x in startPosition.x..endPosition.x) {
                    lightGrid.setLight(Position(x, y), !lightGrid.getLight(Position(x, y)))
                }
            }
        }

        override fun runV2(lightGrid: LightGrid<Int>) {
            for (y in startPosition.y..endPosition.y) {
                for (x in startPosition.x..endPosition.x) {
                    lightGrid.increaseLight(Position(x, y), 2)
                }
            }
        }
    }
}
