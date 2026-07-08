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

import dev.karmakrafts.karbide.writeFloatLeFast
import dev.karmakrafts.karbide.writeIntLeFast
import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.vector.Vector2f
import dev.karmakrafts.kgml.vector.Vector2i
import dev.karmakrafts.kgml.vector.Vector3f
import dev.karmakrafts.kgml.vector.Vector3i
import dev.karmakrafts.kgml.vector.Vector4f
import dev.karmakrafts.kgml.vector.Vector4i
import kotlinx.io.Sink
import kotlinx.io.writeFloat

// Float vectors

/**
 * Writes the given 2D float vector to this sink in component order `x`, `y`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector2f(vector: Vector2f) {
    writeFloat(vector.x)
    writeFloat(vector.y)
}

/**
 * Writes the given 2D float vector to this sink in little-endian component order `x`, `y`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector2fLe(vector: Vector2f) {
    writeFloatLeFast(vector.x)
    writeFloatLeFast(vector.y)
}

/**
 * Writes the given 3D float vector to this sink in component order `x`, `y`, `z`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector3f(vector: Vector3f) {
    writeFloat(vector.x)
    writeFloat(vector.y)
    writeFloat(vector.z)
}

/**
 * Writes the given 3D float vector to this sink in little-endian component order `x`, `y`, `z`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector3fLe(vector: Vector3f) {
    writeFloatLeFast(vector.x)
    writeFloatLeFast(vector.y)
    writeFloatLeFast(vector.z)
}

/**
 * Writes the given 4D float vector to this sink in component order `x`, `y`, `z`, `w`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector4f(vector: Vector4f) {
    writeFloat(vector.x)
    writeFloat(vector.y)
    writeFloat(vector.z)
    writeFloat(vector.w)
}

/**
 * Writes the given 4D float vector to this sink in little-endian component order `x`, `y`, `z`, `w`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector4fLe(vector: Vector4f) {
    writeFloatLeFast(vector.x)
    writeFloatLeFast(vector.y)
    writeFloatLeFast(vector.z)
    writeFloatLeFast(vector.w)
}

// Int vectors

/**
 * Writes the given 2D integer vector to this sink in component order `x`, `y`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector2i(vector: Vector2i) {
    writeInt(vector.x)
    writeInt(vector.y)
}

/**
 * Writes the given 2D integer vector to this sink in little-endian component order `x`, `y`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector2iLe(vector: Vector2i) {
    writeIntLeFast(vector.x)
    writeIntLeFast(vector.y)
}

/**
 * Writes the given 3D integer vector to this sink in component order `x`, `y`, `z`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector3i(vector: Vector3i) {
    writeInt(vector.x)
    writeInt(vector.y)
    writeInt(vector.z)
}

/**
 * Writes the given 3D integer vector to this sink in little-endian component order `x`, `y`, `z`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector3iLe(vector: Vector3i) {
    writeIntLeFast(vector.x)
    writeIntLeFast(vector.y)
    writeIntLeFast(vector.z)
}

/**
 * Writes the given 4D integer vector to this sink in component order `x`, `y`, `z`, `w`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector4i(vector: Vector4i) {
    writeInt(vector.x)
    writeInt(vector.y)
    writeInt(vector.z)
    writeInt(vector.w)
}

/**
 * Writes the given 4D integer vector to this sink in little-endian component order `x`, `y`, `z`, `w`.
 *
 * @param vector The vector to write.
 */
fun Sink.writeVector4iLe(vector: Vector4i) {
    writeIntLeFast(vector.x)
    writeIntLeFast(vector.y)
    writeIntLeFast(vector.z)
    writeIntLeFast(vector.w)
}

// Float matrices

/**
 * Writes the given 2x2 float matrix to this sink in element order
 * `m00`, `m01`, `m10`, `m11`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix2x2f(matrix: Matrix2x2f) {
    writeFloat(matrix.m00)
    writeFloat(matrix.m01)
    writeFloat(matrix.m10)
    writeFloat(matrix.m11)
}

/**
 * Writes the given 2x2 float matrix to this sink in little-endian element order
 * `m00`, `m01`, `m10`, `m11`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix2x2fLe(matrix: Matrix2x2f) {
    writeFloatLeFast(matrix.m00)
    writeFloatLeFast(matrix.m01)
    writeFloatLeFast(matrix.m10)
    writeFloatLeFast(matrix.m11)
}

/**
 * Writes the given 3x3 float matrix to this sink in element order
 * `m00`, `m01`, `m02`, `m10`, `m11`, `m12`, `m20`, `m21`, `m22`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix3x3f(matrix: Matrix3x3f) {
    writeFloat(matrix.m00)
    writeFloat(matrix.m01)
    writeFloat(matrix.m02)
    writeFloat(matrix.m10)
    writeFloat(matrix.m11)
    writeFloat(matrix.m12)
    writeFloat(matrix.m20)
    writeFloat(matrix.m21)
    writeFloat(matrix.m22)
}

/**
 * Writes the given 3x3 float matrix to this sink in little-endian element order
 * `m00`, `m01`, `m02`, `m10`, `m11`, `m12`, `m20`, `m21`, `m22`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix3x3fLe(matrix: Matrix3x3f) {
    writeFloatLeFast(matrix.m00)
    writeFloatLeFast(matrix.m01)
    writeFloatLeFast(matrix.m02)
    writeFloatLeFast(matrix.m10)
    writeFloatLeFast(matrix.m11)
    writeFloatLeFast(matrix.m12)
    writeFloatLeFast(matrix.m20)
    writeFloatLeFast(matrix.m21)
    writeFloatLeFast(matrix.m22)
}

/**
 * Writes the given 4x4 float matrix to this sink in element order
 * `m00`, `m01`, `m02`, `m03`, `m10`, `m11`, `m12`, `m13`,
 * `m20`, `m21`, `m22`, `m23`, `m30`, `m31`, `m32`, `m33`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix4x4f(matrix: Matrix4x4f) {
    writeFloat(matrix.m00)
    writeFloat(matrix.m01)
    writeFloat(matrix.m02)
    writeFloat(matrix.m03)
    writeFloat(matrix.m10)
    writeFloat(matrix.m11)
    writeFloat(matrix.m12)
    writeFloat(matrix.m13)
    writeFloat(matrix.m20)
    writeFloat(matrix.m21)
    writeFloat(matrix.m22)
    writeFloat(matrix.m23)
    writeFloat(matrix.m30)
    writeFloat(matrix.m31)
    writeFloat(matrix.m32)
    writeFloat(matrix.m33)
}

/**
 * Writes the given 4x4 float matrix to this sink in little-endian element order
 * `m00`, `m01`, `m02`, `m03`, `m10`, `m11`, `m12`, `m13`,
 * `m20`, `m21`, `m22`, `m23`, `m30`, `m31`, `m32`, `m33`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix4x4fLe(matrix: Matrix4x4f) {
    writeFloatLeFast(matrix.m00)
    writeFloatLeFast(matrix.m01)
    writeFloatLeFast(matrix.m02)
    writeFloatLeFast(matrix.m03)
    writeFloatLeFast(matrix.m10)
    writeFloatLeFast(matrix.m11)
    writeFloatLeFast(matrix.m12)
    writeFloatLeFast(matrix.m13)
    writeFloatLeFast(matrix.m20)
    writeFloatLeFast(matrix.m21)
    writeFloatLeFast(matrix.m22)
    writeFloatLeFast(matrix.m23)
    writeFloatLeFast(matrix.m30)
    writeFloatLeFast(matrix.m31)
    writeFloatLeFast(matrix.m32)
    writeFloatLeFast(matrix.m33)
}