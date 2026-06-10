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

import dev.karmakrafts.kgml.matrix.Matrix3x3f
import dev.karmakrafts.kgml.util.TO_RAD
import kotlin.math.cos
import kotlin.math.sin

fun Matrix3x3f.Companion.rotationZRad(rad: Float): Matrix3x3f {
    val c = cos(rad)
    val s = sin(rad)
    return Matrix3x3f( // @formatter:off
        c, -s, 0F,
        s, c, 0F,
        0F, 0F, 1F
    ) // @formatter:on
}

@Suppress("NOTHING_TO_INLINE")
inline fun Matrix3x3f.Companion.rotationZ(deg: Float): Matrix3x3f = rotationZRad((deg * TO_RAD).toFloat())

fun Matrix3x3f.Companion.rotationXRad(rad: Float): Matrix3x3f {
    val c = cos(rad)
    val s = sin(rad)
    return Matrix3x3f( // @formatter:off
        1F, 0F, 0F,
        0F, c, -s,
        0F, s, c
    ) // @formatter:on
}

@Suppress("NOTHING_TO_INLINE")
inline fun Matrix3x3f.Companion.rotationX(deg: Float): Matrix3x3f = rotationXRad((deg * TO_RAD).toFloat())

fun Matrix3x3f.Companion.rotationYRad(rad: Float): Matrix3x3f {
    val c = cos(rad)
    val s = sin(rad)
    return Matrix3x3f( // @formatter:off
        c, 0F, s,
        0F, 1F, 0F,
        -s, 0F, c
    ) // @formatter:on
}

@Suppress("NOTHING_TO_INLINE")
inline fun Matrix3x3f.Companion.rotationY(deg: Float): Matrix3x3f = rotationYRad((deg * TO_RAD).toFloat())