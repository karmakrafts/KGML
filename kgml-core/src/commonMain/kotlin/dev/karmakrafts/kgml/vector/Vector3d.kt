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

import dev.karmakrafts.kgml.matrix.Matrix3x3d
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
 * A 3-dimensional double vector.
 *
 * @param x The X component.
 * @param y The Y component.
 * @param z The Z component.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmRecord
data class Vector3d( // @formatter:off
    @JvmField val x: Double,
    @JvmField val y: Double,
    @JvmField val z: Double
) : VectorNd, Comparable<Vector3d> { // @formatter:on
    /**
     * The type of [Vector3d].
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
        override val dimensions: Int = 3

        /**
         * The components of the vector.
         */
        override val components: Array<VectorComponent> =
            arrayOf(VectorComponent.X, VectorComponent.Y, VectorComponent.Z)

        /**
         * A vector with all components set to 0.
         */
        val ZERO: Vector3d = Vector3d()

        /**
         * A vector with all components set to 1.
         */
        val ONE: Vector3d = Vector3d(1.0)

        /**
         * A vector with the X component set to 1 and all other components set to 0.
         */
        val X_POS: Vector3d = Vector3d(1.0, 0.0, 0.0)

        /**
         * A vector with the X component set to -1 and all other components set to 0.
         */
        val X_NEG: Vector3d = Vector3d(-1.0, 0.0, 0.0)

        /**
         * A vector with the Y component set to 1 and all other components set to 0.
         */
        val Y_POS: Vector3d = Vector3d(0.0, 1.0, 0.0)

        /**
         * A vector with the Y component set to -1 and all other components set to 0.
         */
        val Y_NEG: Vector3d = Vector3d(0.0, -1.0, 0.0)

        /**
         * A vector with the Z component set to 1 and all other components set to 0.
         */
        val Z_POS: Vector3d = Vector3d(0.0, 0.0, 1.0)

        /**
         * A vector with the Z component set to -1 and all other components set to 0.
         */
        val Z_NEG: Vector3d = Vector3d(0.0, 0.0, -1.0)

        /**
         * A lexicographical comparator for [Vector3d].
         */
        val lexComparator: Comparator<Vector3d> = { a, b ->
            val (ax, ay, az) = a
            val (bx, by, bz) = b
            if (ax != bx) ax.compareTo(bx)
            else {
                if (ay != by) ay.compareTo(by)
                else az.compareTo(bz)
            }
        }

        /**
         * Creates a [Vector3d] from the given [array].
         *
         * @param array The array to read from.
         * @param offset The offset in the [array].
         * @return The created [Vector3d].
         */
        inline fun fromArray(array: DoubleArray, offset: Int = 0): Vector3d = Vector3d( // @formatter:off
            array[offset],
            array[offset + 1],
            array[offset + 2]
        ) // @formatter:on
    }

    /**
     * Creates a [Vector3d] with all components set to [xyz].
     *
     * @param xyz The value to set all components to.
     */
    constructor(xyz: Double) : this(xyz, xyz, xyz)

    /**
     * Creates a [Vector3d] with all components set to 0.
     */
    constructor() : this(0.0)

    /**
     * The type of [Vector3d].
     */
    override val type: VectorType get() = Vector3d

    /**
     * Adds [xyz] to all components of this vector.
     *
     * @param xyz The value to add.
     * @return The result of the addition.
     */
    inline operator fun plus(xyz: Double): Vector3d = Vector3d(x + xyz, y + xyz, z + xyz)

    /**
     * Adds [other] to this vector.
     *
     * @param other The vector to add.
     * @return The result of the addition.
     */
    inline operator fun plus(other: Vector3d): Vector3d = Vector3d(x + other.x, y + other.y, z + other.z)

    /**
     * Subtracts [xyz] from all components of this vector.
     *
     * @param xyz The value to subtract.
     * @return The result of the subtraction.
     */
    inline operator fun minus(xyz: Double): Vector3d = Vector3d(x - xyz, y - xyz, z - xyz)

    /**
     * Subtracts [other] from this vector.
     *
     * @param other The vector to subtract.
     * @return The result of the subtraction.
     */
    inline operator fun minus(other: Vector3d): Vector3d = Vector3d(x - other.x, y - other.y, z - other.z)

    /**
     * Multiplies all components of this vector by [xyz].
     *
     * @param xyz The value to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(xyz: Double): Vector3d = Vector3d(x * xyz, y * xyz, z * xyz)

    /**
     * Multiplies this vector by [other].
     *
     * @param other The vector to multiply by.
     * @return The result of the multiplication.
     */
    inline operator fun times(other: Vector3d): Vector3d = Vector3d(x * other.x, y * other.y, z * other.z)

    /**
     * Divides all components of this vector by [xyz].
     *
     * @param xyz The value to divide by.
     * @return The result of the division.
     */
    inline operator fun div(xyz: Double): Vector3d = Vector3d(x / xyz, y / xyz, z / xyz)

    /**
     * Divides this vector by [other].
     *
     * @param other The vector to divide by.
     * @return The result of the division.
     */
    inline operator fun div(other: Vector3d): Vector3d = Vector3d(x / other.x, y / other.y, z / other.z)

    /**
     * Calculates the remainder of all components of this vector divided by [xyz].
     *
     * @param xyz The value to divide by.
     * @return The result of the remainder operation.
     */
    inline operator fun rem(xyz: Double): Vector3d = Vector3d(x % xyz, y % xyz, z % xyz)

    /**
     * Calculates the remainder of this vector divided by [other].
     *
     * @param other The vector to divide by.
     * @return The result of the remainder operation.
     */
    inline operator fun rem(other: Vector3d): Vector3d = Vector3d(x % other.x, y % other.y, z % other.z)

    /**
     * Calculates the fused multiply-add of this vector, [b] and [c].
     *
     * @param b The multiplier.
     * @param c The addend.
     * @return The result of the fused multiply-add.
     */
    fun fma(b: Vector3d, c: Vector3d): Vector3d = Vector3d( // @formatter:off
        fma(x, b.x, c.x),
        fma(y, b.y, c.y),
        fma(z, b.z, c.z)
    ) // @formatter:on

    /**
     * Linearly interpolates between this vector and [other] by [factor].
     *
     * @param other The vector to interpolate to.
     * @param factor The interpolation factor.
     * @return The interpolated vector.
     */
    fun lerp(other: Vector3d, factor: Double): Vector3d = Vector3d( // @formatter:off
        fma(other.x - x, factor, x),
        fma(other.y - y, factor, y),
        fma(other.z - z, factor, z)
    ) // @formatter:on

    /**
     * Returns the minimum of this vector and [other] based on their length.
     *
     * @param other The other vector.
     * @return The minimum vector.
     */
    infix fun min(other: Vector3d): Vector3d = when {
        this > other -> other
        else -> this
    }

    /**
     * Returns a vector containing the minimum components of this vector and [other].
     *
     * @param other The other vector.
     * @return The vector containing the minimum components.
     */
    infix fun minComponents(other: Vector3d): Vector3d = Vector3d( // @formatter:off
        min(x, other.x),
        min(y, other.y),
        min(z, other.z)
    ) // @formatter:on

    /**
     * Returns the maximum of this vector and [other] based on their length.
     *
     * @param other The other vector.
     * @return The maximum vector.
     */
    infix fun max(other: Vector3d): Vector3d = when {
        this < other -> other
        else -> this
    }

    /**
     * Returns a vector containing the maximum components of this vector and [other].
     *
     * @param other The other vector.
     * @return The vector containing the maximum components.
     */
    infix fun maxComponents(other: Vector3d): Vector3d = Vector3d( // @formatter:off
        max(x, other.x),
        max(y, other.y),
        max(z, other.z)
    ) // @formatter:on

    /**
     * Calculates the squared distance between this vector and [other].
     *
     * @param other The other vector.
     * @return The squared distance.
     */
    infix fun distanceSq(other: Vector3d): Double {
        val dx = other.x - x
        val dy = other.y - y
        val dz = other.z - z
        return fma(dx, dx, fma(dy, dy, dz * dz))
    }

    /**
     * Calculates the distance between this vector and [other].
     *
     * @param other The other vector.
     * @return The distance.
     */
    inline infix fun distance(other: Vector3d): Double = sqrt(distanceSq(other))

    /**
     * Calculates the angle in radians between this vector and [other].
     *
     * @param other The other vector.
     * @return The angle in radians.
     */
    infix fun angleRad(other: Vector3d): Double {
        val cross = this cross other
        val dot = this dot other
        return atan2(cross.length(), dot)
    }

    /**
     * Calculates the angle in degrees between this vector and [other].
     *
     * @param other The other vector.
     * @return The angle in degrees.
     */
    inline infix fun angle(other: Vector3d): Double = toDegrees(angleRad(other))

    /**
     * Calculates the signed angle in radians between this vector and [other] around the given [axis].
     *
     * @param other The other vector.
     * @param axis The axis to calculate the angle around.
     * @return The signed angle in radians.
     */
    fun signedAngleRad(other: Vector3d, axis: Vector3d): Double {
        val cross = this cross other
        val y = axis dot cross
        val x = this dot other
        return atan2(y, x)
    }

    /**
     * Calculates the signed angle in degrees between this vector and [other] around the given [axis].
     *
     * @param other The other vector.
     * @param axis The axis to calculate the angle around.
     * @return The signed angle in degrees.
     */
    inline fun signedAngle(other: Vector3d, axis: Vector3d): Double = toDegrees(signedAngleRad(other, axis))

    /**
     * Calculates the squared length of this vector.
     *
     * @return The squared length.
     */
    fun lengthSq(): Double = fma(x, x, fma(y, y, z * z))

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
    inline fun normalized(): Vector3d = this / length()

    /**
     * Calculates the dot product of this vector and [other].
     *
     * @param other The other vector.
     * @return The dot product.
     */
    infix fun dot(other: Vector3d): Double = fma(x, other.x, fma(y, other.y, z * other.z))

    /**
     * Calculates the cross product of this vector and [other].
     *
     * @param other The other vector.
     * @return The cross product.
     */
    infix fun cross(other: Vector3d): Vector3d = Vector3d( // @formatter:off
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    ) // @formatter:on

    /**
     * Converts this vector to a [Vector3i].
     *
     * @return The converted vector.
     */
    inline fun toVector3i(): Vector3i = Vector3i(x.toInt(), y.toInt(), z.toInt())

    /**
     * Converts this vector to a [Vector3f].
     *
     * @return The converted vector.
     */
    inline fun toVector3f(): Vector3f = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

    /**
     * Compares this vector to [other] based on their length.
     *
     * @param other The other vector.
     * @return The result of the comparison.
     */
    override operator fun compareTo(other: Vector3d): Int {
        return length().compareTo(other.length())
    }

    /**
     * Multiplies this vector by the given matrix [other].
     *
     * @param other The matrix to multiply by.
     * @return The result of the multiplication.
     */
    operator fun times(other: Matrix3x3d): Vector3d = Vector3d(
        fma(other.m00, x, fma(other.m01, y, other.m02 * z)),
        fma(other.m10, x, fma(other.m11, y, other.m12 * z)),
        fma(other.m20, x, fma(other.m21, y, other.m22 * z))
    )

    override operator fun get(index: Int): Double = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IllegalArgumentException("Invalid vector component $index for Vector3f")
    }

    override operator fun get(component: VectorComponent): Double = when (component) {
        VectorComponent.X -> x
        VectorComponent.Y -> y
        VectorComponent.Z -> z
        else -> throw IllegalArgumentException("Invalid vector component $component for Vector3f")
    }

    override fun toDoubleArray(): DoubleArray = doubleArrayOf(x, y, z)

    /**
     * Swizzles the components of this vector.
     *
     * @param x The component to use for the new X.
     * @param y The component to use for the new Y.
     * @param z The component to use for the new Z.
     * @return The swizzled vector.
     */
    inline fun swizzle( // @formatter:off
        x: VectorComponent,
        y: VectorComponent,
        z: VectorComponent
    ): Vector3d = Vector3d(this[x], this[y], this[z]) // @formatter:on

    /**
     * Swizzles the components of this vector into a [Vector2d].
     *
     * @param x The component to use for the new X.
     * @param y The component to use for the new Y.
     * @return The swizzled vector.
     */
    inline fun swizzle2( // @formatter:off
        x: VectorComponent,
        y: VectorComponent
    ): Vector2d = Vector2d(this[x], this[y]) // @formatter:on
}