package pinger.challenge.parsing

import android.util.Log
import java.util.regex.Pattern

class ApacheLogParser(private var logList: List<String>) {
    private val logEntryPattern =
        "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})] \"(\\w+)(\\s)(\\S+)(.+?)\""
    private val logSearchPattern = Pattern.compile(logEntryPattern)

    fun parseLogsForEachUser(): HashMap<String, MutableList<String>> {
        val listOfLogEntriesPerUser = HashMap<String, MutableList<String>>()

        for (logEntry in logList) {
            val matcher = logSearchPattern.matcher(logEntry)
            if (matcher.lookingAt()) {
                val userIP = matcher.group(1)
                val userPath = matcher.group(7)

                if (userIP != null && userPath != null) {
                    if (listOfLogEntriesPerUser[userIP] == null) {
                        listOfLogEntriesPerUser[userIP] = mutableListOf(userPath)
                    } else {
                        listOfLogEntriesPerUser[userIP]!!.add(userPath)
                    }
                } else {
                    Log.e("ApacheLogParser", "Not a match for user IP and user path")
                }
            }
        }

        return listOfLogEntriesPerUser
    }
}