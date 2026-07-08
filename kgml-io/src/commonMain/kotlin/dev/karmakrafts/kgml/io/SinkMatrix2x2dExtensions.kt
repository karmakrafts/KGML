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
import dev.karmakrafts.kgml.matrix.Matrix2x2d
import kotlinx.io.Sink
import kotlinx.io.writeDouble

/**
 * Writes the given 2x2 double matrix to this sink in element order
 * `m00`, `m01`, `m10`, `m11`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix2x2d(matrix: Matrix2x2d) {
    writeDouble(matrix.m00)
    writeDouble(matrix.m01)
    writeDouble(matrix.m10)
    writeDouble(matrix.m11)
}

/**
 * Writes the given 2x2 double matrix to this sink in little-endian element order
 * `m00`, `m01`, `m10`, `m11`.
 *
 * @param matrix The matrix to write.
 */
fun Sink.writeMatrix2x2dLe(matrix: Matrix2x2d) {
    writeDoubleLeFast(matrix.m00)
    writeDoubleLeFast(matrix.m01)
    writeDoubleLeFast(matrix.m10)
    writeDoubleLeFast(matrix.m11)
}
