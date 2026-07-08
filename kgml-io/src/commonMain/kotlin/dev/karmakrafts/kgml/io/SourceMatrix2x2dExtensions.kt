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

import dev.karmakrafts.karbide.readDoubleLeFast
import dev.karmakrafts.kgml.matrix.Matrix2x2d
import kotlinx.io.Source
import kotlinx.io.readDouble

/**
 * Reads a 2x2 double matrix from this source in element order
 * `m00`, `m01`, `m10`, `m11`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix2x2d(): Matrix2x2d = Matrix2x2d( // @formatter:off
    m00 = readDouble(), m01 = readDouble(),
    m10 = readDouble(), m11 = readDouble()
) // @formatter:on

/**
 * Reads a 2x2 double matrix from this source in little-endian element order
 * `m00`, `m01`, `m10`, `m11`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix2x2dLe(): Matrix2x2d = Matrix2x2d( // @formatter:off
    m00 = readDoubleLeFast(), m01 = readDoubleLeFast(),
    m10 = readDoubleLeFast(), m11 = readDoubleLeFast()
) // @formatter:on
