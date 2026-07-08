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
import dev.karmakrafts.kgml.matrix.Matrix4x4d
import kotlinx.io.Source
import kotlinx.io.readDouble

/**
 * Reads a 4x4 double matrix from this source in element order
 * `m00`, `m01`, `m02`, `m03`, `m10`, `m11`, `m12`, `m13`,
 * `m20`, `m21`, `m22`, `m23`, `m30`, `m31`, `m32`, `m33`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix4x4d(): Matrix4x4d = Matrix4x4d( // @formatter:off
    m00 = readDouble(), m01 = readDouble(), m02 = readDouble(), m03 = readDouble(),
    m10 = readDouble(), m11 = readDouble(), m12 = readDouble(), m13 = readDouble(),
    m20 = readDouble(), m21 = readDouble(), m22 = readDouble(), m23 = readDouble(),
    m30 = readDouble(), m31 = readDouble(), m32 = readDouble(), m33 = readDouble()
) // @formatter:on

/**
 * Reads a 4x4 double matrix from this source in little-endian element order
 * `m00`, `m01`, `m02`, `m03`, `m10`, `m11`, `m12`, `m13`,
 * `m20`, `m21`, `m22`, `m23`, `m30`, `m31`, `m32`, `m33`.
 *
 * @return The read matrix.
 */
fun Source.readMatrix4x4dLe(): Matrix4x4d = Matrix4x4d( // @formatter:off
    m00 = readDoubleLeFast(), m01 = readDoubleLeFast(), m02 = readDoubleLeFast(), m03 = readDoubleLeFast(),
    m10 = readDoubleLeFast(), m11 = readDoubleLeFast(), m12 = readDoubleLeFast(), m13 = readDoubleLeFast(),
    m20 = readDoubleLeFast(), m21 = readDoubleLeFast(), m22 = readDoubleLeFast(), m23 = readDoubleLeFast(),
    m30 = readDoubleLeFast(), m31 = readDoubleLeFast(), m32 = readDoubleLeFast(), m33 = readDoubleLeFast()
) // @formatter:on
