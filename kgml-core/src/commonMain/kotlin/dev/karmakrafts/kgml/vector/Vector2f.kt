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

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import dev.karmakrafts.kgml.util.TO_DEG
import dev.karmakrafts.kgml.util.fma
import kotlin.jvm.JvmField
import kotlin.jvm.JvmRecord
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.reflect.KClass

/**
 * A 2-dimensional float vector.
 *
 * @param x The X component.
 * @param y The Y component.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Vector2f( // @formatter:off
    @JvmField val x: Float,
    @JvmField val y: Float
) : VectorNf, Comparable<Vector2f> { // @formatter:on
    /**
     * The type of [Vector2f].
     */
    companion object : VectorType {
        override val componentType: KClass<*> = Float::class
        override val componentSize: Int = Float.SIZE_BYTES
        override val dimensions: Int = 2
        override val components: Array<VectorComponent> = arrayOf(VectorComponent.X, VectorComponent.Y)

        /**
         * A vector with all components set to 0.
         */
        val zero: Vector2f = Vector2f()

        /**
         * A vector with all components set to 1.
         */
        val one: Vector2f = Vector2f(1F)

        /**
         * A lexicographical comparator for [Vector2f].
         */
        val lexComparator: Comparator<Vector2f> = { a, b ->
            val (ax, ay) = a
            val (bx, by) = b
            if (ax != bx) ax.compareTo(bx)
            else ay.compareTo(by)
        }

        /**
         * Creates a [Vector2f] from the given [array].
         *
         * @param array The array to read from.
         * @param offset The offset in the [array].
         * @return The created [Vector2f].
         */
        inline fun fromArray(array: FloatArray, offset: Int = 0): Vector2f = Vector2f( // @formatter:off
            array[offset],
            array[offset + 1]
        ) // @formatter:on
    }

    /**
     * Creates a [Vector2f] with all components set to [xy].
     *
     * @param xy The value to set all components to.
     */
    constructor(xy: Float) : this(xy, xy)

    /**
     * Creates a [Vector2f] with all components set to 0.
     */
    constructor() : this(0F)

    override val type: VectorType get() = Vector2f

    /**
     * Adds [xy] to all components of this vector.
     *
     * @param xy The value to add.
     * @return The result of the addition.
     */
    inline operator fun plus(xy: Float): Vector2f = Vector2f(x + xy, y + xy)

    /**
     * Adds [other] to this vector.
     *
     * @param other The vector to add.
     * @return The result of the addition.
     */
    inline operator fun plus(other: Vector2f): Vector2f = Vector2f(x + other.x, y + other.y)

    /**
     * Subtracts [xy] from all components of this vector.
     *
     * @param xy The value to subtract.
     * @return The result of the subtraction.
     */
    inline operator fun minus(xy: Float): Vector2f = Vector2f(x - xy, y - xy)

    /**
     * Subtracts [other] from this vector.
     *
     * @param other The vector to subtract.
     * @return The result of the subtraction.
     */
    inline operator fun minus(other: Vector2f): Vector2f = Vector2f(x - other.x, y - other.y)

    /**
     * Multiplies all components of this vector by [xy].
     *
     * @param xy The value to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(xy: Float): Vector2f = Vector2f(x * xy, y * xy)

    /**
     * Multiplies this vector by [other].
     *
     * @param other The vector to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(other: Vector2f): Vector2f = Vector2f(x * other.x, y * other.y)

    /**
     * Divides all components of this vector by [xy].
     *
     * @param xy The value to divide by.
     * @return The result of the division.
     */
    inline operator fun div(xy: Float): Vector2f = Vector2f(x / xy, y / xy)

    /**
     * Divides this vector by [other].
     *
     * @param other The vector to divide by.
     * @return The result of the division.
     */
    inline operator fun div(other: Vector2f): Vector2f = Vector2f(x / other.x, y / other.y)

    /**
     * Calculates the remainder of all components of this vector divided by [xy].
     *
     * @param xy The value to divide by.
     * @return The result of the remainder operation.
     */
    inline operator fun rem(xy: Float): Vector2f = Vector2f(x % xy, y % xy)

    /**
     * Calculates the remainder of this vector divided by [other].
     *
     * @param other The vector to divide by.
     * @return The result of the remainder operation.
     */
    inline operator fun rem(other: Vector2f): Vector2f = Vector2f(x % other.x, y % other.y)

    /**
     * Calculates the fused multiply-add of this vector, [b] and [c].
     *
     * @param b The multiplier.
     * @param c The addend.
     * @return The result of the fused multiply-add.
     */
    fun fma(b: Vector2f, c: Vector2f): Vector2f = Vector2f( // @formatter:off
        fma(x, b.x, c.x),
        fma(y, b.y, c.y)
    ) // @formatter:on

    /**
     * Linearly interpolates between this vector and [other] by [factor].
     *
     * @param other The vector to interpolate to.
     * @param factor The interpolation factor.
     * @return The interpolated vector.
     */
    fun lerp(other: Vector2f, factor: Float): Vector2f = Vector2f( // @formatter:off
        fma(other.x - x, factor, x),
        fma(other.y - y, factor, y)
    ) // @formatter:on

    /**
     * Returns the minimum of this vector and [other] based on their length.
     *
     * @param other The other vector.
     * @return The minimum vector.
     */
    infix fun min(other: Vector2f): Vector2f = when {
        this > other -> other
        else -> this
    }

    /**
     * Returns a vector containing the minimum components of this vector and [other].
     *
     * @param other The other vector.
     * @return The vector containing the minimum components.
     */
    infix fun minComponents(other: Vector2f): Vector2f = Vector2f( // @formatter:off
        min(x, other.x),
        min(y, other.y)
    ) // @formatter:on

    /**
     * Returns the maximum of this vector and [other] based on their length.
     *
     * @param other The other vector.
     * @return The maximum vector.
     */
    infix fun max(other: Vector2f): Vector2f = when {
        this < other -> other
        else -> this
    }

    /**
     * Returns a vector containing the maximum components of this vector and [other].
     *
     * @param other The other vector.
     * @return The vector containing the maximum components.
     */
    infix fun maxComponents(other: Vector2f): Vector2f = Vector2f( // @formatter:off
        max(x, other.x),
        max(y, other.y)
    ) // @formatter:on

    /**
     * Calculates the squared distance between this vector and [other].
     *
     * @param other The other vector.
     * @return The squared distance.
     */
    infix fun distanceSq(other: Vector2f): Float {
        val dx = other.x - x
        val dy = other.y - y
        return fma(dx, dx, dy * dy)
    }

    /**
     * Calculates the distance between this vector and [other].
     *
     * @param other The other vector.
     * @return The distance.
     */
    inline infix fun distance(other: Vector2f): Float = sqrt(distanceSq(other))

    infix fun angleRad(other: Vector2f): Float = atan2(this cross other, this dot other)

    inline infix fun angle(other: Vector2f): Float = (angleRad(other) * TO_DEG).toFloat()

    /**
     * Calculates the squared length of this vector.
     *
     * @return The squared length.
     */
    fun lengthSq(): Float = fma(x, x, y * y)

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
    inline fun normalized(): Vector2f = this / length()

    /**
     * Calculates the dot product of this vector and [other].
     *
     * @param other The other vector.
     * @return The dot product.
     */
    infix fun dot(other: Vector2f): Float = fma(x, other.x, y * other.y)

    /**
     * Calculates the cross product of this vector and [other].
     *
     * @param other The other vector.
     * @return The cross product.
     */
    infix fun cross(other: Vector2f): Float = x * other.y - y * other.x

    /**
     * Converts this vector to a [Vector2i].
     *
     * @return The converted vector.
     */
    inline fun toVector2i(): Vector2i = Vector2i(x.toInt(), y.toInt())

    /**
     * Compares this vector to [other] based on their length.
     *
     * @param other The other vector.
     * @return The result of the comparison.
     */
    override operator fun compareTo(other: Vector2f): Int {
        return length().compareTo(other.length())
    }

    /**
     * Multiplies this vector by the given matrix [other].
     *
     * @param other The matrix to multiply by.
     * @return The result of the multiplication.
     */
    operator fun times(other: Matrix2x2f): Vector2f = Vector2f( // @formatter:off
        fma(other.m00, x, other.m01 * y),
        fma(other.m10, x, other.m11 * y)
    ) // @formatter:on

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
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector2f")
    }

    /**
     * Gets the component for the given [component].
     *
     * @param component The component to get.
     * @return The value of the component.
     * @throws IllegalArgumentException If the component is invalid.
     */
    override operator fun get(component: VectorComponent): Float = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        else -> throw IllegalArgumentException("Invalid vector component $component for Vector2f")
    }

    /**
     * Returns the components of this vector as a [FloatArray].
     *
     * @return The components of this vector.
     */
    override fun toFloatArray(): FloatArray = floatArrayOf(x, y)

    /**
     * Swizzles the components of this vector.
     *
     * @param x The component to use for the new X.
     * @param y The component to use for the new Y.
     * @return The swizzled vector.
     */
    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2f = Vector2f(this[x], this[y]) // @formatter:on
}