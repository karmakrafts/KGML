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
import dev.karmakrafts.kgml.matrix.Matrix3x3f
import kotlinx.io.Source
import kotlinx.io.readFloat

/**
 * Reads a 3x3 float matrix from this source in element order
 * `m00`, `m01`, `m02`, `m10`, `m11`, `m12`, `m20`, `m21`, `m22`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix3x3f(): Matrix3x3f = Matrix3x3f( // @formatter:off
    m00 = readFloat(), m01 = readFloat(), m02 = readFloat(),
    m10 = readFloat(), m11 = readFloat(), m12 = readFloat(),
    m20 = readFloat(), m21 = readFloat(), m22 = readFloat()
) // @formatter:on

/**
 * Reads a 3x3 float matrix from this source in little-endian element order
 * `m00`, `m01`, `m02`, `m10`, `m11`, `m12`, `m20`, `m21`, `m22`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix3x3fLe(): Matrix3x3f = Matrix3x3f( // @formatter:off
    m00 = readFloatLeFast(), m01 = readFloatLeFast(), m02 = readFloatLeFast(),
    m10 = readFloatLeFast(), m11 = readFloatLeFast(), m12 = readFloatLeFast(),
    m20 = readFloatLeFast(), m21 = readFloatLeFast(), m22 = readFloatLeFast()
) // @formatter:on