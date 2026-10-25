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

package dev.karmakrafts.kgml.matrix

import kotlin.jvm.JvmInline

/**
 * Represents a set of properties that can be associated with a matrix.
 *
 * @property value The raw value of the properties.
 */
@Suppress("NOTHING_TO_INLINE")
@JvmInline
public value class MatrixProperties @PublishedApi internal constructor(
    @PublishedApi internal val value: UInt
) {
    /**
     * Constants for common matrix properties.
     */
    public companion object {
        /**
         * No properties.
         */
        public val NONE: MatrixProperties = MatrixProperties(0x0U)

        /**
         * The matrix is an identity matrix.
         */
        public val IDENTITY: MatrixProperties = MatrixProperties(0x1U)

        /**
         * The matrix is an affine matrix.
         */
        public val AFFINE: MatrixProperties = MatrixProperties(0x2U)

        /**
         * The matrix is a translation matrix.
         */
        public val TRANSLATION: MatrixProperties = MatrixProperties(0x4U)

        /**
         * The matrix is a perspective matrix.
         */
        public val PERSPECTIVE: MatrixProperties = MatrixProperties(0x8U)

        /**
         * The matrix is a linear matrix.
         */
        public val LINEAR: MatrixProperties = MatrixProperties(0x10U)

        /**
         * The matrix is a homogeneous matrix.
         */
        public val HOMOGENEOUS: MatrixProperties = MatrixProperties(0x20U)

        /**
         * The matrix is a diagonal matrix.
         */
        public val DIAGONAL: MatrixProperties = MatrixProperties(0x40U)

        /**
         * The matrix is a rotation matrix.
         */
        public val ROTATION: MatrixProperties = MatrixProperties(0x80U)
    }

    /**
     * Whether the matrix is an identity matrix.
     */
    public inline val isIdentity: Boolean get() = IDENTITY in this

    /**
     * Whether the matrix is an affine matrix.
     */
    public inline val isAffine: Boolean get() = AFFINE in this

    /**
     * Whether the matrix is a translation matrix.
     */
    public inline val isTranslation: Boolean get() = TRANSLATION in this

    /**
     * Whether the matrix is a perspective matrix.
     */
    public inline val isPerspective: Boolean get() = PERSPECTIVE in this

    /**
     * Whether the matrix is a linear matrix.
     */
    public inline val isLinear: Boolean get() = LINEAR in this

    /**
     * Whether the matrix is a homogeneous matrix.
     */
    public inline val isHomogeneous: Boolean get() = HOMOGENEOUS in this

    /**
     * Whether the matrix is a diagonal matrix.
     */
    public inline val isDiagonal: Boolean get() = DIAGONAL in this

    /**
     * Whether the matrix is a rotation matrix.
     */
    public inline val isRotation: Boolean get() = ROTATION in this

    /**
     * Combines these properties with another set of properties.
     *
     * @param other The other properties to combine with.
     * @return The combined properties.
     */
    public inline infix fun or(other: MatrixProperties): MatrixProperties = MatrixProperties(value or other.value)

    /**
     * Intersects these properties with another set of properties.
     *
     * @param other The other properties to intersect with.
     * @return The intersected properties.
     */
    public inline infix fun and(other: MatrixProperties): MatrixProperties = MatrixProperties(value and other.value)

    /**
     * Inverts these properties.
     *
     * @return The inverted properties.
     */
    public inline fun inv(): MatrixProperties = MatrixProperties(value.inv())

    /**
     * Checks whether these properties contain the given property.
     *
     * @param other The property to check for.
     * @return Whether these properties contain the given property.
     */
    public inline operator fun contains(other: MatrixProperties): Boolean {
        return value and other.value == other.value
    }
}