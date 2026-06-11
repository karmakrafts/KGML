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

package dev.karmakrafts.kgml.transform

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.util.TO_RAD
import kotlin.math.cos
import kotlin.math.sin

fun Matrix2x2f.Companion.rotationRad(rad: Float): Matrix2x2f {
    val c = cos(rad)
    val s = sin(rad)
    return Matrix2x2f(c, -s, s, c)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Matrix2x2f.Companion.rotation(deg: Float): Matrix2x2f = rotationRad((deg * TO_RAD).toFloat())

fun Matrix2x2f.Companion.scale(scaleX: Float, scaleY: Float): Matrix2x2f = Matrix2x2f( // @formatter:off
    scaleX, 0F,
    0F, scaleY
) // @formatter:on

@Suppress("NOTHING_TO_INLINE")
inline fun Matrix2x2f.Companion.scale(scale: Float): Matrix2x2f = scale(scale, scale)