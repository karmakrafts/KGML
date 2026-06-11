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

@file:Suppress("NOTHING_TO_INLINE")

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

inline fun Matrix3x3f.Companion.rotationY(deg: Float): Matrix3x3f = rotationYRad((deg * TO_RAD).toFloat())

fun Matrix3x3f.Companion.rotationRad( // @formatter:off
    angleX: Float,
    angleY: Float,
    angleZ: Float
): Matrix3x3f = rotationXRad(angleX) * rotationYRad(angleY) * rotationZRad(angleZ) // @formatter:on

inline fun Matrix3x3f.Companion.rotation( // @formatter:off
    angleX: Float,
    angleY: Float,
    angleZ: Float
): Matrix3x3f = rotationRad(
    (angleX * TO_RAD).toFloat(),
    (angleY * TO_RAD).toFloat(),
    (angleZ * TO_RAD).toFloat()
) // @formatter:on

inline operator fun Matrix3x3f.times(quat: Quaternion): Matrix3x3f = this * quat.toRotationMatrix3x3()

fun Matrix3x3f.Companion.translation(x: Float, y: Float): Matrix3x3f = Matrix3x3f( // @formatter:off
    1F, 0F, x,
    0F, 1F, y,
    0F, 0F, 1F
) // @formatter:on

fun Matrix3x3f.Companion.scale(x: Float, y: Float): Matrix3x3f = Matrix3x3f( // @formatter:off
    x,  0F, 0F,
    0F, y,  0F,
    0F, 0F, 1F
) // @formatter:on