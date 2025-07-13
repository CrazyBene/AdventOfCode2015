import utils.combinations

fun main() = Day05.run(RunMode.BOTH)

object Day05 : BasicDay(separateTestFiles = true) {
    override val expectedTestValuePart1 = 2
    override val expectedTestValuePart2 = 2

    override val solvePart1: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        input.count { isNice(it) }
    }

    override val solvePart2: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        input.count { isNice2(it) }
    }

    private fun isNice(word: String): Boolean {
        val min3Vowels = word.count { it in "aeiou" } >= 3
        val doubleLetter = word.zipWithNext().any { (a, b) -> a == b }
        val noBadStrings = listOf("ab", "cd", "pq", "xy").all { it !in word }

        return min3Vowels && doubleLetter && noBadStrings
    }

    data class LetterPairWithIndex(
        val combination: Pair<Char, Char>,
        val startIndex: Int,
    )

    private fun isNice2(word: String): Boolean {
        val containsPairOf2Letters =
            word
                .zipWithNext()
                .withIndex()
                .map { (index, pair) ->
                    LetterPairWithIndex(pair, index)
                }.combinations(2)
                .any { (pair1, pair2) ->
                    pair1.combination == pair2.combination && pair1.startIndex + 2 <= pair2.startIndex
                }
        val repeatWithOneBetween = word.windowed(3).any { it[0] == it[2] }

        return containsPairOf2Letters && repeatWithOneBetween
    }
}
