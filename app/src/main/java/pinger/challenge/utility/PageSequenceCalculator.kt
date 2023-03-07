package pinger.challenge.utility

import android.util.Log

class PageSequenceCalculator {
    private val pageSequenceOccurrences = mutableMapOf<String, Int>()

    fun getMostCommonPageSequences(
        userLogData: HashMap<String, MutableList<String>>,
        numberOfConsecutivePages: Int
    ): List<Pair<String, Int>> {
        pageSequenceOccurrences.clear()

        // for each user's IP address
        for ((_, listOfPages) in userLogData) {
            // for each page in a user's history
            for (i in 0..(listOfPages.size - numberOfConsecutivePages)) {
                try {
                    // for each page sequence (3 pages from index)
                    val consecutivePages = mutableListOf<String>()
                    for (j in i until i + numberOfConsecutivePages) {
                        consecutivePages.add(listOfPages[j])
                    }

                    val pageString = getPageSequenceStringAsLines(consecutivePages)
                    addPageSequenceOrIncrementIfSequenceExists(pageString)
                } catch (ex: IndexOutOfBoundsException) {
                    Log.e("PageSequenceCalculator", "Array was smaller than the consecutive pages requested")
                }
            }
        }
        return sortMap()
    }

    private fun getPageSequenceStringAsLines(pageSequence: List<String>): String {
        return pageSequence.joinToString(separator = "\n")
    }

    // use the sequence as a key and increment count of sequence
    private fun addPageSequenceOrIncrementIfSequenceExists(userPageSequenceString: String) {
        val sequence = pageSequenceOccurrences[userPageSequenceString]
        if (sequence == null) {
            pageSequenceOccurrences[userPageSequenceString] = 1
        } else {
            pageSequenceOccurrences[userPageSequenceString] = sequence + 1
        }
    }

    private fun sortMap(): List<Pair<String, Int>> {
        return pageSequenceOccurrences.toList().sortedByDescending { it.second }
    }
}