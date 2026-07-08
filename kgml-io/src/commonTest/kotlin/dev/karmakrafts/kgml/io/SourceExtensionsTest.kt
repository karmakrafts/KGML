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

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.vector.Vector2f
import dev.karmakrafts.kgml.vector.Vector3f
import dev.karmakrafts.kgml.vector.Vector4f
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlin.test.Test
import kotlin.test.assertEquals

class SourceExtensionsTest {
    @Test
    fun `readVector2f should read big endian vector`() = assertReads(
        bytesOf(1F, -2F), Vector2f(1F, -2F)
    ) { readVector2f() }

    @Test
    fun `readVector2fLe should read little endian vector`() = assertReads(
        bytesOf(1F, -2F, littleEndian = true), Vector2f(1F, -2F)
    ) { readVector2fLe() }

    @Test
    fun `readVector3f should read big endian vector`() = assertReads(
        bytesOf(1F, -2F, 3.5F), Vector3f(1F, -2F, 3.5F)
    ) { readVector3f() }

    @Test
    fun `readVector3fLe should read little endian vector`() = assertReads(
        bytesOf(1F, -2F, 3.5F, littleEndian = true), Vector3f(1F, -2F, 3.5F)
    ) { readVector3fLe() }

    @Test
    fun `readVector4f should read big endian vector`() = assertReads(
        bytesOf(1F, -2F, 3.5F, -4.25F), Vector4f(1F, -2F, 3.5F, -4.25F)
    ) { readVector4f() }

    @Test
    fun `readVector4fLe should read little endian vector`() = assertReads(
        bytesOf(1F, -2F, 3.5F, -4.25F, littleEndian = true), Vector4f(1F, -2F, 3.5F, -4.25F)
    ) { readVector4fLe() }

    @Test
    fun `readMatrix2x2f should read big endian matrix`() = assertReads(
        bytesOf(1F, 2F, 3F, 4F), Matrix2x2f(1F, 2F, 3F, 4F)
    ) { readMatrix2x2f() }

    @Test
    fun `readMatrix2x2fLe should read little endian matrix`() = assertReads(
        bytesOf(1F, 2F, 3F, 4F, littleEndian = true), Matrix2x2f(1F, 2F, 3F, 4F)
    ) { readMatrix2x2fLe() }

    @Test
    fun `readMatrix3x3f should read big endian matrix`() = assertReads(
        bytesOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F), Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
    ) { readMatrix3x3f() }

    @Test
    fun `readMatrix3x3fLe should read little endian matrix`() = assertReads(
        bytesOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, littleEndian = true), Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
    ) { readMatrix3x3fLe() }

    @Test
    fun `readMatrix4x4f should read big endian matrix`() = assertReads(
        bytesOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F),
        Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F)
    ) { readMatrix4x4f() }

    @Test
    fun `readMatrix4x4fLe should read little endian matrix`() = assertReads(
        bytesOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F, littleEndian = true),
        Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F)
    ) { readMatrix4x4fLe() }

    private fun <T> assertReads(bytes: ByteArray, expected: T, reader: Source.() -> T) {
        val buffer = Buffer().apply { write(bytes) }
        assertEquals(expected, buffer.reader())
    }

    private fun bytesOf(vararg values: Float, littleEndian: Boolean = false): ByteArray {
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
}