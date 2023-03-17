package pinger.challenge.parser

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ApacheLogParserTest {

    private lateinit var logList : MutableList<String>
    private var parser = ApacheLogParser()

    @Before
    fun setUp() {
        // given
        logList = mutableListOf(
            "123.4.5.9 - - [03/Sep/2013:18:34:48 -0600] \"GET /team/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n",
            "123.4.5.6 - - [03/Sep/2013:18:34:58 -0600] \"GET /products/car/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n",
            "123.4.5.8 - - [03/Sep/2013:18:35:08 -0600] \"GET /products/desk/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.65 Safari/537.36\"\n",
            "123.4.5.4 - - [03/Sep/2013:18:37:28 -0600] \"GET /team/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n",
            "123.4.5.9 - - [03/Sep/2013:18:40:08 -0600] \"GET /products/desk/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.65 Safari/537.36\"\n"
        )
    }

    @Test
    fun `String is parsed correctly`() {
        // when
        val parsedMap = parser.parseLogsForEachUser(logList)

        // then
        assertEquals("/team/, /products/desk/", parsedMap["123.4.5.9"]?.joinToString())
        assertEquals("/products/car/", parsedMap["123.4.5.6"]?.joinToString())
        assertEquals("/products/desk/", parsedMap["123.4.5.8"]?.joinToString())
        assertEquals("/team/", parsedMap["123.4.5.4"]?.joinToString())
    }

    @Test
    fun `List size is correct when parsed`() {
        // when
        val parsedMap = parser.parseLogsForEachUser(logList)

        // then
        assertEquals(2, parsedMap["123.4.5.9"]?.size)
        assertEquals(1, parsedMap["123.4.5.6"]?.size)
        assertEquals(1, parsedMap["123.4.5.8"]?.size)
        assertEquals(1, parsedMap["123.4.5.4"]?.size)
    }
}