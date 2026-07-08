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
import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.vector.Vector2f
import dev.karmakrafts.kgml.vector.Vector3f
import dev.karmakrafts.kgml.vector.Vector4f
import kotlinx.io.Source
import kotlinx.io.readFloat

fun Source.readVector2f(): Vector2f = Vector2f(
    x = readFloat(), y = readFloat()
)

fun Source.readVector2fLe(): Vector2f = Vector2f(
    x = readFloatLeFast(), y = readFloatLeFast()
)

fun Source.readVector3f(): Vector3f = Vector3f(
    x = readFloat(), y = readFloat(), z = readFloat()
)

fun Source.readVector3fLe(): Vector3f = Vector3f(
    x = readFloatLeFast(), y = readFloatLeFast(), z = readFloatLeFast()
)

fun Source.readVector4f(): Vector4f = Vector4f(
    x = readFloat(), y = readFloat(), z = readFloat(), w = readFloat()
)

fun Source.readVector4fLe(): Vector4f = Vector4f(
    x = readFloatLeFast(), y = readFloatLeFast(), z = readFloatLeFast(), w = readFloatLeFast()
)

fun Source.readMatrix2x2f(): Matrix2x2f = Matrix2x2f(
    m00 = readFloat(), m01 = readFloat(), m10 = readFloat(), m11 = readFloat()
)

fun Source.readMatrix2x2fLe(): Matrix2x2f = Matrix2x2f(
    m00 = readFloatLeFast(), m01 = readFloatLeFast(), m10 = readFloatLeFast(), m11 = readFloatLeFast()
)

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