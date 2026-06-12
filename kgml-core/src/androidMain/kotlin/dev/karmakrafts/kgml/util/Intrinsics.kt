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

@file:JvmName("Intrinsics$")

package dev.karmakrafts.kgml.util

import android.os.Build
import dev.karmakrafts.kgml.vector.Vector4f

@Suppress("NOTHING_TO_INLINE")
actual inline fun fma(a: Float, b: Float, c: Float): Float {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return Math.fma(a, b, c)
    }
    return a * b + c
}

@Suppress("NOTHING_TO_INLINE")
actual inline fun fma(a: Double, b: Double, c: Double): Double {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return Math.fma(a, b, c)
    }
    return a * b + c
}

internal actual fun fma4( // @formatter:off
    ax: Float, ay: Float, az: Float, aw: Float,
    bx: Float, by: Float, bz: Float, bw: Float,
    cx: Float, cy: Float, cz: Float, cw: Float
): Vector4f = Vector4f(
    fma(ax, bx, cx),
    fma(ay, by, cy),
    fma(az, bz, cz),
    fma(aw, bw, cw)
) // @formatter:on