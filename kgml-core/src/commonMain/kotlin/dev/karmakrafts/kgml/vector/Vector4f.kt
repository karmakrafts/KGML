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
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.reflect.KClass

/**
 * A 4-dimensional float vector.
 *
 * @param x The X component.
 * @param y The Y component.
 * @param z The Z component.
 * @param w The W component.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Vector4f( // @formatter:off
    @JvmField val x: Float,
    @JvmField val y: Float,
    @JvmField val z: Float,
    @JvmField val w: Float
) : VectorNf, Comparable<Vector4f> { // @formatter:on
    /**
     * The type of [Vector4f].
     */
    companion object : VectorType {
        /**
         * The type of the components in the vector.
         */
        override val componentType: KClass<*> = Float::class

        /**
         * The size of a single component in bytes.
         */
        override val componentSize: Int = Float.SIZE_BYTES

        /**
         * The number of dimensions in the vector.
         */
        override val dimensions: Int = 4

        /**
         * The components of the vector.
         */
        override val components: Array<VectorComponent> = VectorComponent.entries.toTypedArray()

        /**
         * A vector with all components set to 0.
         */
        val ZERO: Vector4f = Vector4f()

        /**
         * A vector with all components set to 1.
         */
        val ONE: Vector4f = Vector4f(1F)

        /**
         * A vector with the X component set to 1 and all other components set to 0.
         */
        val X_POS: Vector4f = Vector4f(1F, 0F, 0F, 0F)

        /**
         * A vector with the X component set to -1 and all other components set to 0.
         */
        val X_NEG: Vector4f = Vector4f(-1F, 0F, 0F, 0F)

        /**
         * A vector with the Y component set to 1 and all other components set to 0.
         */
        val Y_POS: Vector4f = Vector4f(0F, 1F, 0F, 0F)

        /**
         * A vector with the Y component set to -1 and all other components set to 0.
         */
        val Y_NEG: Vector4f = Vector4f(0F, -1F, 0F, 0F)

        /**
         * A vector with the Z component set to 1 and all other components set to 0.
         */
        val Z_POS: Vector4f = Vector4f(0F, 0F, 1F, 0F)

        /**
         * A vector with the Z component set to -1 and all other components set to 0.
         */
        val Z_NEG: Vector4f = Vector4f(0F, 0F, -1F, 0F)

        /**
         * A vector with the W component set to 1 and all other components set to 0.
         */
        val W_POS: Vector4f = Vector4f(0F, 0F, 0F, 1F)

        /**
         * A vector with the W component set to -1 and all other components set to 0.
         */
        val W_NEG: Vector4f = Vector4f(0F, 0F, 0F, -1F)

        /**
         * A lexicographical comparator for [Vector4f].
         */
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

        /**
         * Creates a [Vector4f] from the given [array].
         *
         * @param array The array to read from.
         * @param offset The offset in the [array].
         * @return The created [Vector4f].
         */
        inline fun fromArray(array: FloatArray, offset: Int = 0): Vector4f = Vector4f( // @formatter:off
            array[offset],
            array[offset + 1],
            array[offset + 2],
            array[offset + 3]
        ) // @formatter:on
    }

    /**
     * Creates a [Vector4f] with all components set to [xyzw].
     *
     * @param xyzw The value to set all components to.
     */
    constructor(xyzw: Float) : this(xyzw, xyzw, xyzw, xyzw)

    /**
     * Creates a [Vector4f] with all components set to 0.
     */
    constructor() : this(0F)

    /**
     * The type of [Vector4f].
     */
    override val type: VectorType get() = Vector4f

    /**
     * Adds [xyzw] to all components of this vector.
     *
     * @param xyzw The value to add.
     * @return The result of the addition.
     */
    inline operator fun plus(xyzw: Float): Vector4f = Vector4f(x + xyzw, y + xyzw, z + xyzw, w + xyzw)

    /**
     * Adds [other] to this vector.
     *
     * @param other The vector to add.
     * @return The result of the addition.
     */
    inline operator fun plus(other: Vector4f): Vector4f = Vector4f(x + other.x, y + other.y, z + other.z, w + other.w)

    /**
     * Subtracts [xyzw] from all components of this vector.
     *
     * @param xyzw The value to subtract.
     * @return The result of the subtraction.
     */
    inline operator fun minus(xyzw: Float): Vector4f = Vector4f(x - xyzw, y - xyzw, z - xyzw, w - xyzw)

    /**
     * Subtracts [other] from this vector.
     *
     * @param other The vector to subtract.
     * @return The result of the subtraction.
     */
    inline operator fun minus(other: Vector4f): Vector4f = Vector4f(x - other.x, y - other.y, z - other.z, w - other.w)

    /**
     * Multiplies all components of this vector by [xyzw].
     *
     * @param xyzw The value to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(xyzw: Float): Vector4f = Vector4f(x * xyzw, y * xyzw, z * xyzw, w * xyzw)

    /**
     * Multiplies this vector by [other].
     *
     * @param other The vector to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(other: Vector4f): Vector4f = Vector4f(x * other.x, y * other.y, z * other.z, w * other.w)

    /**
     * Divides all components of this vector by [xyzw].
     *
     * @param xyzw The value to divide by.
     * @return The result of the division.
     */
    inline operator fun div(xyzw: Float): Vector4f = Vector4f(x / xyzw, y / xyzw, z / xyzw, w / xyzw)

    /**
     * Divides this vector by [other].
     *
     * @param other The vector to divide by.
     * @return The result of the division.
     */
    inline operator fun div(other: Vector4f): Vector4f = Vector4f(x / other.x, y / other.y, z / other.z, w / other.w)

    /**
     * Calculates the remainder of all components of this vector divided by [xyzw].
     *
     * @param xyzw The value to divide by.
     * @return The result of the remainder operation.
     */
    inline operator fun rem(xyzw: Float): Vector4f = Vector4f(x % xyzw, y % xyzw, z % xyzw, w % xyzw)

    /**
     * Calculates the remainder of this vector divided by [other].
     *
     * @param other The vector to divide by.
     * @return The result of the remainder operation.
     */
    inline operator fun rem(other: Vector4f): Vector4f = Vector4f(x % other.x, y % other.y, z % other.z, w % other.w)

    /**
     * Calculates the fused multiply-add of this vector, [b] and [c].
     *
     * @param b The multiplier.
     * @param c The addend.
     * @return The result of the fused multiply-add.
     */
    fun fma(b: Vector4f, c: Vector4f): Vector4f = Vector4f( // @formatter:off
        fma(x, b.x, c.x),
        fma(y, b.y, c.y),
        fma(z, b.z, c.z),
        fma(w, b.w, c.w)
    ) // @formatter:on

    /**
     * Linearly interpolates between this vector and [other] by [factor].
     *
     * @param other The vector to interpolate to.
     * @param factor The interpolation factor.
     * @return The interpolated vector.
     */
    fun lerp(other: Vector4f, factor: Float): Vector4f = Vector4f( // @formatter:off
        fma(other.x - x, factor, x),
        fma(other.y - y, factor, y),
        fma(other.z - z, factor, z),
        fma(other.w - w, factor, w)
    ) // @formatter:on

    /**
     * Returns the minimum of this vector and [other] based on their length.
     *
     * @param other The other vector.
     * @return The minimum vector.
     */
    infix fun min(other: Vector4f): Vector4f = when {
        this > other -> other
        else -> this
    }

    /**
     * Returns a vector containing the minimum components of this vector and [other].
     *
     * @param other The other vector.
     * @return The vector containing the minimum components.
     */
    infix fun minComponents(other: Vector4f): Vector4f = Vector4f( // @formatter:off
        min(x, other.x),
        min(y, other.y),
        min(z, other.z),
        min(w, other.w)
    ) // @formatter:on

    /**
     * Returns the maximum of this vector and [other] based on their length.
     *
     * @param other The other vector.
     * @return The maximum vector.
     */
    infix fun max(other: Vector4f): Vector4f = when {
        this < other -> other
        else -> this
    }

    /**
     * Returns a vector containing the maximum components of this vector and [other].
     *
     * @param other The other vector.
     * @return The vector containing the maximum components.
     */
    infix fun maxComponents(other: Vector4f): Vector4f = Vector4f( // @formatter:off
        max(x, other.x),
        max(y, other.y),
        max(z, other.z),
        max(w, other.w)
    ) // @formatter:on

    /**
     * Calculates the squared distance between this vector and [other].
     *
     * @param other The other vector.
     * @return The squared distance.
     */
    infix fun distanceSq(other: Vector4f): Float {
        val dx = other.x - x
        val dy = other.y - y
        val dz = other.z - z
        val dw = other.w - w
        return fma(dx, dx, fma(dy, dy, fma(dz, dz, dw * dw)))
    }

    /**
     * Calculates the distance between this vector and [other].
     *
     * @param other The other vector.
     * @return The distance.
     */
    inline infix fun distance(other: Vector4f): Float = sqrt(distanceSq(other))

    /**
     * Calculates the squared length of this vector.
     *
     * @return The squared length.
     */
    fun lengthSq(): Float = fma(x, x, fma(y, y, fma(z, z, w * w)))

    /**
     * Calculates the length of this vector.
     *
     * @return The length.
     */
    inline fun length(): Float = sqrt(lengthSq())

    /**
     * Returns a normalized version of this vector.
     *
     * @return The normalized vector.
     */
    inline fun normalized(): Vector4f = this / length()

    /**
     * Calculates the dot product of this vector and [other].
     *
     * @param other The other vector.
     * @return The dot product.
     */
    infix fun dot(other: Vector4f): Float = fma(x, other.x, fma(y, other.y, fma(z, other.z, w * other.w)))

    /**
     * Converts this vector to a [Vector4i].
     *
     * @return The converted vector.
     */
    inline fun toVector4i(): Vector4i = Vector4i(x.toInt(), y.toInt(), z.toInt(), w.toInt())

    /**
     * Compares this vector to [other] based on their length.
     *
     * @param other The other vector.
     * @return The result of the comparison.
     */
    override operator fun compareTo(other: Vector4f): Int {
        return length().compareTo(other.length())
    }

    /**
     * Multiplies this vector by the given matrix [other].
     *
     * @param other The matrix to multiply by.
     * @return The result of the multiplication.
     */
    operator fun times(other: Matrix4x4f): Vector4f = Vector4f(
        fma(other.m00, x, fma(other.m01, y, fma(other.m02, z, other.m03 * w))),
        fma(other.m10, x, fma(other.m11, y, fma(other.m12, z, other.m13 * w))),
        fma(other.m20, x, fma(other.m21, y, fma(other.m22, z, other.m23 * w))),
        fma(other.m30, x, fma(other.m31, y, fma(other.m32, z, other.m33 * w)))
    )

    /**
     * Gets the component at the given [index].
     *
     * @param index The index of the component.
     * @return The value of the component.
     * @throws IllegalArgumentException If the index is invalid.
     */
    override operator fun get(index: Int): Float = when (index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector4f")
    }

    /**
     * Gets the component for the given [component].
     *
     * @param component The component to get.
     * @return The value of the component.
     */
    override operator fun get(component: VectorComponent): Float = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        VectorComponent.Z -> z
        VectorComponent.W -> w
    }

    /**
     * Returns the components of this vector as a [FloatArray].
     *
     * @return The components of this vector.
     */
    override fun toFloatArray(): FloatArray = floatArrayOf(x, y, z, w)

    /**
     * Swizzles the components of this vector.
     *
     * @param x The component to use for the new X.
     * @param y The component to use for the new Y.
     * @param z The component to use for the new Z.
     * @param w The component to use for the new W.
     * @return The swizzled vector.
     */
    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent,
        w: VectorComponent
    ): Vector4f = Vector4f(this[x], this[y], this[z], this[w]) // @formatter:on

    /**
     * Swizzles the components of this vector into a [Vector3f].
     *
     * @param x The component to use for the new X.
     * @param y The component to use for the new Y.
     * @param z The component to use for the new Z.
     * @return The swizzled vector.
     */
    inline fun swizzle3( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent
    ): Vector3f = Vector3f(this[x], this[y], this[z]) // @formatter:on

    /**
     * Swizzles the components of this vector into a [Vector2f].
     *
     * @param x The component to use for the new X.
     * @param y The component to use for the new Y.
     * @return The swizzled vector.
     */
    inline fun swizzle2( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2f = Vector2f(this[x], this[y]) // @formatter:on
}