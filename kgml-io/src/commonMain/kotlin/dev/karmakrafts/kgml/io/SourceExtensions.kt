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

import dev.karmakrafts.karbide.readFloatLeFast
import dev.karmakrafts.karbide.readIntLeFast
import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.vector.Vector2f
import dev.karmakrafts.kgml.vector.Vector2i
import dev.karmakrafts.kgml.vector.Vector3f
import dev.karmakrafts.kgml.vector.Vector3i
import dev.karmakrafts.kgml.vector.Vector4f
import dev.karmakrafts.kgml.vector.Vector4i
import kotlinx.io.Source
import kotlinx.io.readFloat

// Float vectors

/**
 * Reads a 2D float vector from this source in component order `x`, `y`.
 *
 * @return The read vector.
 */
fun Source.readVector2f(): Vector2f = Vector2f(
    x = readFloat(), y = readFloat()
)

/**
 * Reads a 2D float vector from this source in little-endian component order `x`, `y`.
 *
 * @return The read vector.
 */
fun Source.readVector2fLe(): Vector2f = Vector2f(
    x = readFloatLeFast(), y = readFloatLeFast()
)

/**
 * Reads a 3D float vector from this source in component order `x`, `y`, `z`.
 *
 * @return The read vector.
 */
fun Source.readVector3f(): Vector3f = Vector3f(
    x = readFloat(), y = readFloat(), z = readFloat()
)

/**
 * Reads a 3D float vector from this source in little-endian component order `x`, `y`, `z`.
 *
 * @return The read vector.
 */
fun Source.readVector3fLe(): Vector3f = Vector3f(
    x = readFloatLeFast(), y = readFloatLeFast(), z = readFloatLeFast()
)

/**
 * Reads a 4D float vector from this source in component order `x`, `y`, `z`, `w`.
 *
 * @return The read vector.
 */
fun Source.readVector4f(): Vector4f = Vector4f(
    x = readFloat(), y = readFloat(), z = readFloat(), w = readFloat()
)

/**
 * Reads a 4D float vector from this source in little-endian component order `x`, `y`, `z`, `w`.
 *
 * @return The read vector.
 */
fun Source.readVector4fLe(): Vector4f = Vector4f(
    x = readFloatLeFast(), y = readFloatLeFast(), z = readFloatLeFast(), w = readFloatLeFast()
)

// Int vectors

/**
 * Reads a 2D integer vector from this source in component order `x`, `y`.
 *
 * @return The read vector.
 */
fun Source.readVector2i(): Vector2i = Vector2i(
    x = readInt(), y = readInt()
)

/**
 * Reads a 2D integer vector from this source in little-endian component order `x`, `y`.
 *
 * @return The read vector.
 */
fun Source.readVector2iLe(): Vector2i = Vector2i(
    x = readIntLeFast(), y = readIntLeFast()
)

/**
 * Reads a 3D integer vector from this source in component order `x`, `y`, `z`.
 *
 * @return The read vector.
 */
fun Source.readVector3i(): Vector3i = Vector3i(
    x = readInt(), y = readInt(), z = readInt()
)

/**
 * Reads a 3D integer vector from this source in little-endian component order `x`, `y`, `z`.
 *
 * @return The read vector.
 */
fun Source.readVector3iLe(): Vector3i = Vector3i(
    x = readIntLeFast(), y = readIntLeFast(), z = readIntLeFast()
)

/**
 * Reads a 4D integer vector from this source in component order `x`, `y`, `z`, `w`.
 *
 * @return The read vector.
 */
fun Source.readVector4i(): Vector4i = Vector4i(
    x = readInt(), y = readInt(), z = readInt(), w = readInt()
)

/**
 * Reads a 4D integer vector from this source in little-endian component order `x`, `y`, `z`, `w`.
 *
 * @return The read vector.
 */
fun Source.readVector4iLe(): Vector4i = Vector4i(
    x = readIntLeFast(), y = readIntLeFast(), z = readIntLeFast(), w = readIntLeFast()
)

// Float matrices

/**
 * Reads a 2x2 float matrix from this source in element order
 * `m00`, `m01`, `m10`, `m11`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix2x2f(): Matrix2x2f = Matrix2x2f(
    m00 = readFloat(), m01 = readFloat(), m10 = readFloat(), m11 = readFloat()
)

/**
 * Reads a 2x2 float matrix from this source in little-endian element order
 * `m00`, `m01`, `m10`, `m11`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix2x2fLe(): Matrix2x2f = Matrix2x2f(
    m00 = readFloatLeFast(), m01 = readFloatLeFast(), m10 = readFloatLeFast(), m11 = readFloatLeFast()
)

/**
 * Reads a 3x3 float matrix from this source in element order
 * `m00`, `m01`, `m02`, `m10`, `m11`, `m12`, `m20`, `m21`, `m22`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix3x3f(): Matrix3x3f = Matrix3x3f(
    m00 = readFloat(),
    m01 = readFloat(),
    m02 = readFloat(),
    m10 = readFloat(),
    m11 = readFloat(),
    m12 = readFloat(),
    m20 = readFloat(),
    m21 = readFloat(),
    m22 = readFloat()
)

/**
 * Reads a 3x3 float matrix from this source in little-endian element order
 * `m00`, `m01`, `m02`, `m10`, `m11`, `m12`, `m20`, `m21`, `m22`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix3x3fLe(): Matrix3x3f = Matrix3x3f(
    m00 = readFloatLeFast(),
    m01 = readFloatLeFast(),
    m02 = readFloatLeFast(),
    m10 = readFloatLeFast(),
    m11 = readFloatLeFast(),
    m12 = readFloatLeFast(),
    m20 = readFloatLeFast(),
    m21 = readFloatLeFast(),
    m22 = readFloatLeFast()
)

/**
 * Reads a 4x4 float matrix from this source in element order
 * `m00`, `m01`, `m02`, `m03`, `m10`, `m11`, `m12`, `m13`,
 * `m20`, `m21`, `m22`, `m23`, `m30`, `m31`, `m32`, `m33`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix4x4f(): Matrix4x4f = Matrix4x4f(
    m00 = readFloat(),
    m01 = readFloat(),
    m02 = readFloat(),
    m03 = readFloat(),
    m10 = readFloat(),
    m11 = readFloat(),
    m12 = readFloat(),
    m13 = readFloat(),
    m20 = readFloat(),
    m21 = readFloat(),
    m22 = readFloat(),
    m23 = readFloat(),
    m30 = readFloat(),
    m31 = readFloat(),
    m32 = readFloat(),
    m33 = readFloat()
)

/**
 * Reads a 4x4 float matrix from this source in little-endian element order
 * `m00`, `m01`, `m02`, `m03`, `m10`, `m11`, `m12`, `m13`,
 * `m20`, `m21`, `m22`, `m23`, `m30`, `m31`, `m32`, `m33`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix4x4fLe(): Matrix4x4f = Matrix4x4f(
    m00 = readFloatLeFast(),
    m01 = readFloatLeFast(),
    m02 = readFloatLeFast(),
    m03 = readFloatLeFast(),
    m10 = readFloatLeFast(),
    m11 = readFloatLeFast(),
    m12 = readFloatLeFast(),
    m13 = readFloatLeFast(),
    m20 = readFloatLeFast(),
    m21 = readFloatLeFast(),
    m22 = readFloatLeFast(),
    m23 = readFloatLeFast(),
    m30 = readFloatLeFast(),
    m31 = readFloatLeFast(),
    m32 = readFloatLeFast(),
    m33 = readFloatLeFast()
)