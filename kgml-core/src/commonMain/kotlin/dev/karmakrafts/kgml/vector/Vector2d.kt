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

import dev.karmakrafts.kgml.matrix.Matrix2x2d
import dev.karmakrafts.kgml.util.fma
import dev.karmakrafts.kgml.util.toDegrees
import kotlin.jvm.JvmField
import kotlin.jvm.JvmRecord
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.reflect.KClass

/**
 * A 2-dimensional double vector.
 *
 * @param x The X component.
 * @param y The Y component.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Vector2d( // @formatter:off
    @JvmField val x: Double,
    @JvmField val y: Double
) : VectorNd, Comparable<Vector2d> { // @formatter:on
    /**
     * The type of [Vector2d].
     */
    companion object : VectorType {
        /**
         * The type of the components in the vector.
         */
        override val componentType: KClass<*> = Double::class

        /**
         * The size of a single component in bytes.
         */
        override val componentSize: Int = Double.SIZE_BYTES

        /**
         * The number of dimensions in the vector.
         */
        override val dimensions: Int = 2

        /**
         * The components of the vector.
         */
        override val components: Array<VectorComponent> = arrayOf(VectorComponent.X, VectorComponent.Y)

        /**
         * A vector with all components set to 0.
         */
        val ZERO: Vector2d = Vector2d()

        /**
         * A vector with all components set to 1.
         */
        val ONE: Vector2d = Vector2d(1.0)

        /**
         * A vector with the X component set to 1 and all other components set to 0.
         */
        val X_POS: Vector2d = Vector2d(1.0, 0.0)

        /**
         * A vector with the X component set to -1 and all other components set to 0.
         */
        val X_NEG: Vector2d = Vector2d(-1.0, 0.0)

        /**
         * A vector with the Y component set to 1 and all other components set to 0.
         */
        val Y_POS: Vector2d = Vector2d(0.0, 1.0)

        /**
         * A vector with the Y component set to -1 and all other components set to 0.
         */
        val Y_NEG: Vector2d = Vector2d(0.0, -1.0)

        /**
         * A lexicographical comparator for [Vector2d].
         */
        val lexComparator: Comparator<Vector2d> = { a, b ->
            val (ax, ay) = a
            val (bx, by) = b
            if (ax != bx) ax.compareTo(bx)
            else ay.compareTo(by)
        }

        /**
         * Creates a [Vector2d] from the given [array].
         *
         * @param array The array to read from.
         * @param offset The offset in the [array].
         * @return The created [Vector2d].
         */
        inline fun fromArray(array: DoubleArray, offset: Int = 0): Vector2d = Vector2d( // @formatter:off
            array[offset],
            array[offset + 1]
        ) // @formatter:on
    }

    /**
     * Creates a [Vector2d] with all components set to [xy].
     *
     * @param xy The value to set all components to.
     */
    constructor(xy: Double) : this(xy, xy)

    /**
     * Creates a [Vector2d] with all components set to 0.
     */
    constructor() : this(0.0)

    /**
     * The type of [Vector2d].
     */
    override val type: VectorType get() = Vector2d

    /**
     * Adds [xy] to all components of this vector.
     *
     * @param xy The value to add.
     * @return The result of the addition.
     */
    inline operator fun plus(xy: Double): Vector2d = Vector2d(x + xy, y + xy)

    /**
     * Adds [other] to this vector.
     *
     * @param other The vector to add.
     * @return The result of the addition.
     */
    inline operator fun plus(other: Vector2d): Vector2d = Vector2d(x + other.x, y + other.y)

    /**
     * Subtracts [xy] from all components of this vector.
     *
     * @param xy The value to subtract.
     * @return The result of the subtraction.
     */
    inline operator fun minus(xy: Double): Vector2d = Vector2d(x - xy, y - xy)

    /**
     * Subtracts [other] from this vector.
     *
     * @param other The vector to subtract.
     * @return The result of the subtraction.
     */
    inline operator fun minus(other: Vector2d): Vector2d = Vector2d(x - other.x, y - other.y)

    /**
     * Multiplies all components of this vector by [xy].
     *
     * @param xy The value to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(xy: Double): Vector2d = Vector2d(x * xy, y * xy)

    /**
     * Multiplies this vector by [other].
     *
     * @param other The vector to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(other: Vector2d): Vector2d = Vector2d(x * other.x, y * other.y)

    /**
     * Divides all components of this vector by [xy].
     *
     * @param xy The value to divide by.
     * @return The result of the division.
     */
    inline operator fun div(xy: Double): Vector2d = Vector2d(x / xy, y / xy)

    /**
     * Divides this vector by [other].
     *
     * @param other The vector to divide by.
     * @return The result of the division.
     */
    inline operator fun div(other: Vector2d): Vector2d = Vector2d(x / other.x, y / other.y)

    /**
     * Calculates the remainder of all components of this vector divided by [xy].
     *
     * @param xy The value to divide by.
     * @return The result of the remainder operation.
     */
    inline operator fun rem(xy: Double): Vector2d = Vector2d(x % xy, y % xy)

    /**
     * Calculates the remainder of this vector divided by [other].
     *
     * @param other The vector to divide by.
     * @return The result of the remainder operation.
     */
    inline operator fun rem(other: Vector2d): Vector2d = Vector2d(x % other.x, y % other.y)

    /**
     * Calculates the fused multiply-add of this vector, [b] and [c].
     *
     * @param b The multiplier.
     * @param c The addend.
     * @return The result of the fused multiply-add.
     */
    fun fma(b: Vector2d, c: Vector2d): Vector2d = Vector2d( // @formatter:off
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
    fun lerp(other: Vector2d, factor: Double): Vector2d = Vector2d( // @formatter:off
        fma(other.x - x, factor, x),
        fma(other.y - y, factor, y)
    ) // @formatter:on

    /**
     * Returns the minimum of this vector and [other] based on their length.
     *
     * @param other The other vector.
     * @return The minimum vector.
     */
    infix fun min(other: Vector2d): Vector2d = when {
        this > other -> other
        else -> this
    }

    /**
     * Returns a vector containing the minimum components of this vector and [other].
     *
     * @param other The other vector.
     * @return The vector containing the minimum components.
     */
    infix fun minComponents(other: Vector2d): Vector2d = Vector2d( // @formatter:off
        min(x, other.x),
        min(y, other.y)
    ) // @formatter:on

    /**
     * Returns the maximum of this vector and [other] based on their length.
     *
     * @param other The other vector.
     * @return The maximum vector.
     */
    infix fun max(other: Vector2d): Vector2d = when {
        this < other -> other
        else -> this
    }

    /**
     * Returns a vector containing the maximum components of this vector and [other].
     *
     * @param other The other vector.
     * @return The vector containing the maximum components.
     */
    infix fun maxComponents(other: Vector2d): Vector2d = Vector2d( // @formatter:off
        max(x, other.x),
        max(y, other.y)
    ) // @formatter:on

    /**
     * Calculates the squared distance between this vector and [other].
     *
     * @param other The other vector.
     * @return The squared distance.
     */
    infix fun distanceSq(other: Vector2d): Double {
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
    inline infix fun distance(other: Vector2d): Double = sqrt(distanceSq(other))

    /**
     * Calculates the angle in radians between this vector and [other].
     *
     * @param other The other vector.
     * @return The angle in radians.
     */
    infix fun angleRad(other: Vector2d): Double = atan2(this cross other, this dot other)

    /**
     * Calculates the angle in degrees between this vector and [other].
     *
     * @param other The other vector.
     * @return The angle in degrees.
     */
    inline infix fun angle(other: Vector2d): Double = toDegrees(angleRad(other))

    /**
     * Calculates the squared length of this vector.
     *
     * @return The squared length.
     */
    fun lengthSq(): Double = fma(x, x, y * y)

    /**
     * Calculates the length of this vector.
     *
     * @return The length.
     */
    inline fun length(): Double = sqrt(lengthSq())

    /**
     * Returns a normalized version of this vector.
     *
     * @return The normalized vector.
     */
    inline fun normalized(): Vector2d = this / length()

    /**
     * Calculates the dot product of this vector and [other].
     *
     * @param other The other vector.
     * @return The dot product.
     */
    infix fun dot(other: Vector2d): Double = fma(x, other.x, y * other.y)

    /**
     * Calculates the cross product of this vector and [other].
     *
     * @param other The other vector.
     * @return The cross product.
     */
    infix fun cross(other: Vector2d): Double = x * other.y - y * other.x

    /**
     * Converts this vector to a [Vector2i].
     *
     * @return The converted vector.
     */
    inline fun toVector2i(): Vector2i = Vector2i(x.toInt(), y.toInt())

    /**
     * Converts this vector to a [Vector2f].
     *
     * @return The converted vector.
     */
    inline fun toVector2f(): Vector2f = Vector2f(x.toFloat(), y.toFloat())

    /**
     * Compares this vector to [other] based on their length.
     *
     * @param other The other vector.
     * @return The result of the comparison.
     */
    override operator fun compareTo(other: Vector2d): Int {
        return length().compareTo(other.length())
    }

    /**
     * Multiplies this vector by the given matrix [other].
     *
     * @param other The matrix to multiply by.
     * @return The result of the multiplication.
     */
    operator fun times(other: Matrix2x2d): Vector2d = Vector2d( // @formatter:off
        fma(other.m00, x, other.m01 * y),
        fma(other.m10, x, other.m11 * y)
    ) // @formatter:on

    override operator fun get(index: Int): Double = when (index) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector2f")
    }

    override operator fun get(component: VectorComponent): Double = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        else -> throw IllegalArgumentException("Invalid vector component $component for Vector2f")
    }

    override fun toDoubleArray(): DoubleArray = doubleArrayOf(x, y)

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
    ): Vector2d = Vector2d(this[x], this[y]) // @formatter:on
}