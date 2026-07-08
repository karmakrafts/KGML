/*
 * Copyright 2026 Karma Krafts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.karmakrafts.kgml.io

import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.readByteArray
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal fun assertWrites(expected: ByteArray, writer: Sink.() -> Unit) {
    val buffer = Buffer()
    buffer.writer()
    assertContentEquals(expected, buffer.readByteArray())
}

internal fun <T> assertReads(bytes: ByteArray, expected: T, reader: Source.() -> T) {
    val buffer = Buffer().apply { write(bytes) }
    assertEquals(expected, buffer.reader())
}

internal fun bytesOf(vararg values: Float, littleEndian: Boolean = false): ByteArray {
    val bytes = ByteArray(values.size * Float.SIZE_BYTES)
    var index = 0
    for (value in values) {
        val bits = value.toBits()
        if (littleEndian) {
            bytes[index++] = bits.toByte()
            bytes[index++] = (bits ushr 8).toByte()
            bytes[index++] = (bits ushr 16).toByte()
            bytes[index++] = (bits ushr 24).toByte()
        }
        else {
            bytes[index++] = (bits ushr 24).toByte()
            bytes[index++] = (bits ushr 16).toByte()
            bytes[index++] = (bits ushr 8).toByte()
            bytes[index++] = bits.toByte()
        }
    }
    return bytes
}

internal fun bytesOf(vararg values: Int, littleEndian: Boolean = false): ByteArray {
    val bytes = ByteArray(values.size * Int.SIZE_BYTES)
    var index = 0
    for (value in values) {
        if (littleEndian) {
            bytes[index++] = value.toByte()
            bytes[index++] = (value ushr 8).toByte()
            bytes[index++] = (value ushr 16).toByte()
            bytes[index++] = (value ushr 24).toByte()
        }
        else {
            bytes[index++] = (value ushr 24).toByte()
            bytes[index++] = (value ushr 16).toByte()
            bytes[index++] = (value ushr 8).toByte()
            bytes[index++] = value.toByte()
        }
    }
    return bytes
}

internal fun bytesOf(vararg values: Double, littleEndian: Boolean = false): ByteArray {
    val bytes = ByteArray(values.size * Double.SIZE_BYTES)
    var index = 0
    for (value in values) {
        val bits = value.toBits()
        if (littleEndian) {
            bytes[index++] = bits.toByte()
            bytes[index++] = (bits ushr 8).toByte()
            bytes[index++] = (bits ushr 16).toByte()
            bytes[index++] = (bits ushr 24).toByte()
            bytes[index++] = (bits ushr 32).toByte()
            bytes[index++] = (bits ushr 40).toByte()
            bytes[index++] = (bits ushr 48).toByte()
            bytes[index++] = (bits ushr 56).toByte()
        }
        else {
            bytes[index++] = (bits ushr 56).toByte()
            bytes[index++] = (bits ushr 48).toByte()
            bytes[index++] = (bits ushr 40).toByte()
            bytes[index++] = (bits ushr 32).toByte()
            bytes[index++] = (bits ushr 24).toByte()
            bytes[index++] = (bits ushr 16).toByte()
            bytes[index++] = (bits ushr 8).toByte()
            bytes[index++] = bits.toByte()
        }
    }
    return bytes
}