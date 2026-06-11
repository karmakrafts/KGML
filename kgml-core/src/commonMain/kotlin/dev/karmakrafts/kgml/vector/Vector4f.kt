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

package dev.karmakrafts.kgml.vector

import dev.karmakrafts.kgml.matrix.Matrix4x4f
import dev.karmakrafts.kgml.util.fma
import kotlin.jvm.JvmField
import kotlin.jvm.JvmRecord
import kotlin.math.sqrt
import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Vector4f( // @formatter:off
    @JvmField val x: Float,
    @JvmField val y: Float,
    @JvmField val z: Float,
    @JvmField val w: Float
) : VectorNf, Comparable<Vector4f> { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val dimensions: Int = 4
        override val components: Array<VectorComponent> = VectorComponent.entries.toTypedArray()

        val zero: Vector4f = Vector4f()
        val one: Vector4f = Vector4f(1F)

        val lexComparator: Comparator<Vector4f> = { a, b ->
            val (ax, ay, az, aw) = a
            val (bx, by, bz, bw) = b
            if (ax != bx) ax.compareTo(bx)
            else {
                if (ay != by) ay.compareTo(by)
                else {
                    if (az != bz) az.compareTo(bz)
                    else aw.compareTo(bw)
                }
            }
        }

        inline fun fromArray(array: FloatArray, offset: Int = 0): Vector4f = Vector4f( // @formatter:off
            array[offset],
            array[offset + 1],
            array[offset + 2],
            array[offset + 3]
        ) // @formatter:on
    }

    constructor(xyzw: Float) : this(xyzw, xyzw, xyzw, xyzw)
    constructor() : this(0F)

    override val type: VectorType get() = Vector4f

    inline operator fun plus(xyzw: Float): Vector4f = Vector4f(x + xyzw, y + xyzw, z + xyzw, w + xyzw)
    inline operator fun plus(other: Vector4f): Vector4f = Vector4f(x + other.x, y + other.y, z + other.z, w + other.w)

    inline operator fun minus(xyzw: Float): Vector4f = Vector4f(x - xyzw, y - xyzw, z - xyzw, w - xyzw)
    inline operator fun minus(other: Vector4f): Vector4f = Vector4f(x - other.x, y - other.y, z - other.z, w - other.w)

    inline operator fun times(xyzw: Float): Vector4f = Vector4f(x * xyzw, y * xyzw, z * xyzw, w * xyzw)
    inline operator fun times(other: Vector4f): Vector4f = Vector4f(x * other.x, y * other.y, z * other.z, w * other.w)

    inline operator fun div(xyzw: Float): Vector4f = Vector4f(x / xyzw, y / xyzw, z / xyzw, w / xyzw)
    inline operator fun div(other: Vector4f): Vector4f = Vector4f(x / other.x, y / other.y, z / other.z, w / other.w)

    inline operator fun rem(xyzw: Float): Vector4f = Vector4f(x % xyzw, y % xyzw, z % xyzw, w % xyzw)
    inline operator fun rem(other: Vector4f): Vector4f = Vector4f(x % other.x, y % other.y, z % other.z, w % other.w)

    fun fma(b: Vector4f, c: Vector4f): Vector4f = Vector4f( // @formatter:off
        fma(x, b.x, c.x),
        fma(y, b.y, c.y),
        fma(z, b.z, c.z),
        fma(w, b.w, c.w)
    ) // @formatter:on

    fun lerp(other: Vector4f, factor: Float): Vector4f = Vector4f( // @formatter:off
        fma(other.x - x, factor, x),
        fma(other.y - y, factor, y),
        fma(other.z - z, factor, z),
        fma(other.w - w, factor, w)
    ) // @formatter:on

    fun distanceSq(other: Vector4f): Float {
        val dx = other.x - x
        val dy = other.y - y
        val dz = other.z - z
        val dw = other.w - w
        return fma(dx, dx, fma(dy, dy, fma(dz, dz, dw * dw)))
    }

    inline fun distance(other: Vector4f): Float = sqrt(distanceSq(other))

    fun lengthSq(): Float = fma(x, x, fma(y, y, fma(z, z, w * w)))
    inline fun length(): Float = sqrt(lengthSq())

    inline fun normalized(): Vector4f = this / length()

    infix fun dot(other: Vector4f): Float = fma(x, other.x, fma(y, other.y, fma(z, other.z, w * other.w)))

    inline fun toVector4i(): Vector4i = Vector4i(x.toInt(), y.toInt(), z.toInt(), w.toInt())

    override operator fun compareTo(other: Vector4f): Int {
        return length().compareTo(other.length())
    }

    operator fun times(other: Matrix4x4f): Vector4f = Vector4f(
        fma(other.m00, x, fma(other.m01, y, fma(other.m02, z, other.m03 * w))),
        fma(other.m10, x, fma(other.m11, y, fma(other.m12, z, other.m13 * w))),
        fma(other.m20, x, fma(other.m21, y, fma(other.m22, z, other.m23 * w))),
        fma(other.m30, x, fma(other.m31, y, fma(other.m32, z, other.m33 * w)))
    )

    override operator fun get(index: Int): Float = when (index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector4f")
    }

    override operator fun get(component: VectorComponent): Float = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        VectorComponent.Z -> z
        VectorComponent.W -> w
    }

    override fun toFloatArray(): FloatArray = floatArrayOf(x, y, z, w)

    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent,
        w: VectorComponent
    ): Vector4f = Vector4f(this[x], this[y], this[z], this[w]) // @formatter:on

    inline fun swizzle3( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent
    ): Vector3f = Vector3f(this[x], this[y], this[z]) // @formatter:on

    inline fun swizzle2( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2f = Vector2f(this[x], this[y]) // @formatter:on
}