package com.example.skrabooking.utils

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

object Fb2Parser {

    fun parseFb2Stream(inputStream: InputStream): String {
        return try {
            val parser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parseFb2Content(parser)
        } catch (e: Exception) {
            "Ошибка парсинга FB2: ${e.message}"
        } finally {
            inputStream.close()
        }
    }

    private fun parseFb2Content(parser: XmlPullParser): String {
        val textContent = StringBuilder()
        var eventType = parser.eventType
        var inBody = false
        var inParagraph = false

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "body" -> inBody = true
                        "p" -> inParagraph = true
                    }
                }
                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        "body" -> inBody = false
                        "p" -> {
                            inParagraph = false
                            textContent.append("\n\n") // Добавляем переносы между абзацами
                        }
                    }
                }
                XmlPullParser.TEXT -> {
                    if (inBody && inParagraph) {
                        textContent.append(parser.text)
                    }
                }
            }
            eventType = parser.next()
        }
        return textContent.toString().trim()
    }
}