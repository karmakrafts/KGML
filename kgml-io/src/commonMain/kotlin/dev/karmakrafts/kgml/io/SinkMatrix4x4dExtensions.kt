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

import dev.karmakrafts.karbide.writeDoubleLeFast
import dev.karmakrafts.kgml.matrix.Matrix4x4d
import kotlinx.io.Sink
import kotlinx.io.writeDouble

/**
 * Writes the given 4x4 double matrix to this sink in element order
 * `m00`, `m01`, `m02`, `m03`, `m10`, `m11`, `m12`, `m13`,
 * `m20`, `m21`, `m22`, `m23`, `m30`, `m31`, `m32`, `m33`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix4x4d(matrix: Matrix4x4d) {
    writeDouble(matrix.m00)
    writeDouble(matrix.m01)
    writeDouble(matrix.m02)
    writeDouble(matrix.m03)
    writeDouble(matrix.m10)
    writeDouble(matrix.m11)
    writeDouble(matrix.m12)
    writeDouble(matrix.m13)
    writeDouble(matrix.m20)
    writeDouble(matrix.m21)
    writeDouble(matrix.m22)
    writeDouble(matrix.m23)
    writeDouble(matrix.m30)
    writeDouble(matrix.m31)
    writeDouble(matrix.m32)
    writeDouble(matrix.m33)
}

/**
 * Writes the given 4x4 double matrix to this sink in little-endian element order
 * `m00`, `m01`, `m02`, `m03`, `m10`, `m11`, `m12`, `m13`,
 * `m20`, `m21`, `m22`, `m23`, `m30`, `m31`, `m32`, `m33`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix4x4dLe(matrix: Matrix4x4d) {
    writeDoubleLeFast(matrix.m00)
    writeDoubleLeFast(matrix.m01)
    writeDoubleLeFast(matrix.m02)
    writeDoubleLeFast(matrix.m03)
    writeDoubleLeFast(matrix.m10)
    writeDoubleLeFast(matrix.m11)
    writeDoubleLeFast(matrix.m12)
    writeDoubleLeFast(matrix.m13)
    writeDoubleLeFast(matrix.m20)
    writeDoubleLeFast(matrix.m21)
    writeDoubleLeFast(matrix.m22)
    writeDoubleLeFast(matrix.m23)
    writeDoubleLeFast(matrix.m30)
    writeDoubleLeFast(matrix.m31)
    writeDoubleLeFast(matrix.m32)
    writeDoubleLeFast(matrix.m33)
}
