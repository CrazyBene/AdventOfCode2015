package utils

/**
 * Generates all combinations of the elements of the given list for the requested size.
 * Note: Combinations do not include all their permutations!
 * @receiver The list to take elements from
 * @param size The size of the combinations to create
 * @return A sequence of all combinations
 */
fun <T> List<T>.combinations(size: Int): Sequence<List<T>> =
    when (size) {
        0 -> emptySequence()
        1 -> asSequence().map { listOf(it) }
        else ->
            sequence {
                this@combinations.forEachIndexed { index, element ->
                    val head = listOf(element)
                    val tail = this@combinations.subList(index + 1, this@combinations.size)
                    tail.combinations(size - 1).forEach { tailCombination ->
                        yield(head + tailCombination)
                    }
                }
            }
    }
