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
import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.util.TO_RAD

inline operator fun Matrix4x4f.times(quat: Quaternion): Matrix4x4f = this * quat.toRotationMatrix4x4()

inline fun Matrix4x4f.Companion.rotationXRad(rad: Float): Matrix4x4f = Matrix3x3f.rotationXRad(rad).extend()
inline fun Matrix4x4f.Companion.rotationX(deg: Float): Matrix4x4f = rotationXRad((deg * TO_RAD).toFloat())

inline fun Matrix4x4f.Companion.rotationYRad(rad: Float): Matrix4x4f = Matrix3x3f.rotationYRad(rad).extend()
inline fun Matrix4x4f.Companion.rotationY(deg: Float): Matrix4x4f = rotationYRad((deg * TO_RAD).toFloat())

inline fun Matrix4x4f.Companion.rotationZRad(rad: Float): Matrix4x4f = Matrix3x3f.rotationZRad(rad).extend()
inline fun Matrix4x4f.Companion.rotationZ(deg: Float): Matrix4x4f = rotationZRad((deg * TO_RAD).toFloat())

fun Matrix4x4f.Companion.translation(x: Float, y: Float, z: Float): Matrix4x4f = Matrix4x4f( // @formatter:off
    1F, 0F, 0F, x,
    0F, 1F, 0F, y,
    0F, 0F, 1F, z,
    0F, 0F, 0F, 1F
) // @formatter:on