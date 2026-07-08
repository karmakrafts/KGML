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
import dev.karmakrafts.kgml.matrix.Matrix3x3f
import kotlinx.io.Sink
import kotlinx.io.writeFloat

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