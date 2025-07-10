import kotlin.math.min

fun main() = Day02.run(RunMode.BOTH)

object Day02 : BasicDay() {
    override val expectedTestValuePart1 = 101
    override val expectedTestValuePart2 = 48

    override val solvePart1: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        input.sumOf { line ->
            val (length, width, height) = parseInputToDimensions(line)

            val lengthWidthArea = length * width
            val widthHeightArea = width * height
            val heightLengthArea = height * length

            val area = 2 * (lengthWidthArea + widthHeightArea + heightLengthArea)

            val smallestSideArea = min(min(lengthWidthArea, widthHeightArea), heightLengthArea)

            area + smallestSideArea
        }
    }

    override val solvePart2: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        input.sumOf { line ->
            val (length, width, height) = parseInputToDimensions(line)

            val (smallestDimension, middleDimension) = listOf(length, width, height).sorted().take(2)
            val smallestPerimeter = 2 * (smallestDimension + middleDimension)

            val volume = length * width * height

            smallestPerimeter + volume
        }
    }

    private data class BoxDimensions(
        val length: Int,
        val width: Int,
        val height: Int,
    )

    private fun parseInputToDimensions(line: String): BoxDimensions =
        line.split("x").let {
            if (it.size != 3) error("Could not split line $line in length, width, height, result is $it.")

            BoxDimensions(it[0].toInt(), it[1].toInt(), it[2].toInt())
        }
}
