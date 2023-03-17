package pinger.challenge.utility

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PageSequenceCalculatorTest {

    private val pageSequenceCalculator = PageSequenceCalculator()

    @Test
    fun `Most common page sequences are correct`() {
        // given
        val logData = HashMap<String, MutableList<String>>()
        logData["123.4.5.9"] = mutableListOf("/team/", "/team/", "/products/desk/", "/products/", "/products/phone/", "/contact/", "/admin/")
        logData["123.4.5.8"] = mutableListOf("/products/desk/", "/products/phone/", "/contact/", "/admin/")

        // when
        val mostCommonSequences = pageSequenceCalculator.getMostCommonPageSequences(logData, 3)

        // then
        assertEquals(2, mostCommonSequences[0].second)
        assertEquals("/products/phone/\n/contact/\n/admin/", mostCommonSequences[0].first)
    }

    /*
        User A: Page 1
        User B: Page 1 User B: Page 2 User B: Page 3 User B: Page 2
        User A: Page 2 User A: Page 3 User A: Page 4 User A: Page 1 User A: Page 2
    */
    @Test
    fun `Test sample input`() {
        // given
        val logData = HashMap<String, MutableList<String>>()
        logData["User A"] = mutableListOf("Page 1", "Page 2", "Page 3", "Page 4", "Page 1", "Page 2")
        logData["User B"] = mutableListOf("Page 1", "Page 2", "Page 3", "Page 2")

        // when
        val mostCommonSequences = pageSequenceCalculator.getMostCommonPageSequences(logData, 3)

        // then
        assertEquals(2, mostCommonSequences[0].second)
        assertEquals("Page 1\nPage 2\nPage 3", mostCommonSequences[0].first)
    }
}