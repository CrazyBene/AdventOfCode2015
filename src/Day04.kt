import java.security.MessageDigest

fun main() = Day04.run(RunMode.BOTH)

object Day04 : BasicDay() {
    override val expectedTestValuePart1 = 609043

    override val solvePart1: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        val secretKey = input.first()

        var potentialSolution = 1
        while (true) {
            val md5Hash = calculateMd5Hash("$secretKey$potentialSolution")
            if (md5Hash.startsWith("00000")) {
                break
            }

            potentialSolution++
        }

        potentialSolution
    }

    override val solvePart2: ((input: List<String>, isTestRun: Boolean) -> Int) = { input, _ ->
        val secretKey = input.first()

        var potentialSolution = 1
        while (true) {
            val md5Hash = calculateMd5Hash("$secretKey$potentialSolution")
            if (md5Hash.startsWith("000000")) {
                break
            }

            potentialSolution++
        }

        potentialSolution
    }

    fun calculateMd5Hash(potentialSolution: String): String {
        val md = MessageDigest.getInstance("MD5")
        val hashBytes = md.digest(potentialSolution.toByteArray())

        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
