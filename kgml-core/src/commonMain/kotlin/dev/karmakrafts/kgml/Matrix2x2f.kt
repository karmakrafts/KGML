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

package dev.karmakrafts.kgml

import kotlin.jvm.JvmField
import kotlin.reflect.KClass

data class Matrix2x2f( // @formatter:off
    @JvmField val m00: Float,
    @JvmField val m01: Float,
    @JvmField val m10: Float,
    @JvmField val m11: Float
) : MatrixNxNf { // @formatter:on
    companion object : MatrixType {
        override val elementType: KClass<*> = Float::class
        override val rows: Int = 2
        override val columns: Int = 2
    }

    override val type: MatrixType get() = Matrix2x2f
}
