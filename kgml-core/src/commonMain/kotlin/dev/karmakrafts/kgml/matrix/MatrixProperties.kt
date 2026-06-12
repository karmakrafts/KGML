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

@Suppress("NOTHING_TO_INLINE")
@JvmInline
value class MatrixProperties @PublishedApi internal constructor(
    @PublishedApi internal val value: UInt
) {
    companion object {
        val NONE: MatrixProperties = MatrixProperties(0x0U)
        val IDENTITY: MatrixProperties = MatrixProperties(0x1U)
        val AFFINE: MatrixProperties = MatrixProperties(0x2U)
        val TRANSLATION: MatrixProperties = MatrixProperties(0x4U)
    }

    inline val isIdentity: Boolean get() = IDENTITY in this
    inline val isAffine: Boolean get() = AFFINE in this
    inline val isTranslation: Boolean get() = TRANSLATION in this

    inline infix fun or(other: MatrixProperties): MatrixProperties = MatrixProperties(value or other.value)
    inline infix fun and(other: MatrixProperties): MatrixProperties = MatrixProperties(value and other.value)

    inline fun inv(): MatrixProperties = MatrixProperties(value.inv())

    inline operator fun contains(other: MatrixProperties): Boolean {
        return value and other.value == other.value
    }
}