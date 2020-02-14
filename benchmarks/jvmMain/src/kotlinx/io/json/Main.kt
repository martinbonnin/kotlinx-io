@file:UseExperimental(ImplicitReflectionSerializer::class, ExperimentalStdlibApi::class)

package kotlinx.io.json

import kotlinx.io.*
import kotlinx.io.bytes.*
import kotlinx.io.json.data.*
import kotlinx.io.json.utils.*
import kotlinx.io.text.*
import kotlinx.serialization.*
import java.io.*
import java.io.ByteArrayInputStream
import kotlin.reflect.*

private val twitter = File("benchmarks/commonMain/resources/twitter.json").readText()
private val canada = File("benchmarks/commonMain/resources/canada.json").readText()
private val canadaBytes = canada.toByteArray()

private val smallTextBytesASCII = "ABC.".toByteArray()

private val largeTextBytesASCII = ByteArray(smallTextBytesASCII.size * 10000 * 10000) {
    smallTextBytesASCII[it % smallTextBytesASCII.size]
}
private val largeTextPacketASCII = BytesOutput().apply {
    writeByteArray(largeTextBytesASCII)
}

private fun parseTwitter(): Twitter =
    KxJson.parse(twitter, typeOf<Twitter>(), Twitter::class.java)

private fun parseCanadaInput(): Canada =
    KxJson.parse(ByteArrayInput(canadaBytes), typeOf<Canada>(), Canada::class.java)

private fun parseCanadaGson(): Canada =
    GsonJson.parse(ByteArrayInputStream(canadaBytes), typeOf<Canada>(), Canada::class.java)

private fun readLargeASCII(): String =
    largeTextPacketASCII.createInput().readUtf8String()

private fun constructorLargeASCII(): String =
    String(largeTextBytesASCII, Charsets.UTF_8)

private fun readReader() {
    val x = ByteArrayInputStream(largeTextBytesASCII)
        .bufferedReader()
        .readText()
}

fun main() {
    while (true) {
        parseCanadaInput()
//        parseCanadaGson()
    }
}
