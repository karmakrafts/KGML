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
import dev.karmakrafts.kgml.vector.Vector2i
import dev.karmakrafts.kgml.vector.Vector3f
import dev.karmakrafts.kgml.vector.Vector3i
import dev.karmakrafts.kgml.vector.Vector4f
import dev.karmakrafts.kgml.vector.Vector4i
import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.readByteArray
import kotlin.test.Test
import kotlin.test.assertContentEquals

class SinkExtensionsTest {
    @Test
    fun `writeVector2f should write big endian vector`() = assertWrites(
        bytesOf(1F, -2F)
    ) { writeVector2f(Vector2f(1F, -2F)) }

    @Test
    fun `writeVector2fLe should write little endian vector`() = assertWrites(
        bytesOf(1F, -2F, littleEndian = true)
    ) { writeVector2fLe(Vector2f(1F, -2F)) }

    @Test
    fun `writeVector3f should write big endian vector`() = assertWrites(
        bytesOf(1F, -2F, 3.5F)
    ) { writeVector3f(Vector3f(1F, -2F, 3.5F)) }

    @Test
    fun `writeVector3fLe should write little endian vector`() = assertWrites(
        bytesOf(1F, -2F, 3.5F, littleEndian = true)
    ) { writeVector3fLe(Vector3f(1F, -2F, 3.5F)) }

    @Test
    fun `writeVector4f should write big endian vector`() = assertWrites(
        bytesOf(1F, -2F, 3.5F, -4.25F)
    ) { writeVector4f(Vector4f(1F, -2F, 3.5F, -4.25F)) }

    @Test
    fun `writeVector4fLe should write little endian vector`() = assertWrites(
        bytesOf(1F, -2F, 3.5F, -4.25F, littleEndian = true)
    ) { writeVector4fLe(Vector4f(1F, -2F, 3.5F, -4.25F)) }

    @Test
    fun `writeVector2i should write big endian vector`() = assertWrites(
        bytesOf(1, -2)
    ) { writeVector2i(Vector2i(1, -2)) }

    @Test
    fun `writeVector2iLe should write little endian vector`() = assertWrites(
        bytesOf(1, -2, littleEndian = true)
    ) { writeVector2iLe(Vector2i(1, -2)) }

    @Test
    fun `writeVector3i should write big endian vector`() = assertWrites(
        bytesOf(1, -2, 3)
    ) { writeVector3i(Vector3i(1, -2, 3)) }

    @Test
    fun `writeVector3iLe should write little endian vector`() = assertWrites(
        bytesOf(1, -2, 3, littleEndian = true)
    ) { writeVector3iLe(Vector3i(1, -2, 3)) }

    @Test
    fun `writeVector4i should write big endian vector`() = assertWrites(
        bytesOf(1, -2, 3, -4)
    ) { writeVector4i(Vector4i(1, -2, 3, -4)) }

    @Test
    fun `writeVector4iLe should write little endian vector`() = assertWrites(
        bytesOf(1, -2, 3, -4, littleEndian = true)
    ) { writeVector4iLe(Vector4i(1, -2, 3, -4)) }

    @Test
    fun `writeMatrix2x2f should write big endian matrix`() = assertWrites(
        bytesOf(1F, 2F, 3F, 4F)
    ) { writeMatrix2x2f(Matrix2x2f(1F, 2F, 3F, 4F)) }

    @Test
    fun `writeMatrix2x2fLe should write little endian matrix`() = assertWrites(
        bytesOf(1F, 2F, 3F, 4F, littleEndian = true)
    ) { writeMatrix2x2fLe(Matrix2x2f(1F, 2F, 3F, 4F)) }

    @Test
    fun `writeMatrix3x3f should write big endian matrix`() = assertWrites(
        bytesOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)
    ) { writeMatrix3x3f(Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)) }

    @Test
    fun `writeMatrix3x3fLe should write little endian matrix`() = assertWrites(
        bytesOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, littleEndian = true)
    ) { writeMatrix3x3fLe(Matrix3x3f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F)) }

    @Test
    fun `writeMatrix4x4f should write big endian matrix`() = assertWrites(
        bytesOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F)
    ) {
        writeMatrix4x4f(Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F))
    }

    @Test
    fun `writeMatrix4x4fLe should write little endian matrix`() = assertWrites(
        bytesOf(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F, littleEndian = true)
    ) {
        writeMatrix4x4fLe(Matrix4x4f(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F, 11F, 12F, 13F, 14F, 15F, 16F))
    }

    private fun assertWrites(expected: ByteArray, writer: Sink.() -> Unit) {
        val buffer = Buffer()
        buffer.writer()
        assertContentEquals(expected, buffer.readByteArray())
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

    private fun bytesOf(vararg values: Int, littleEndian: Boolean = false): ByteArray {
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
}