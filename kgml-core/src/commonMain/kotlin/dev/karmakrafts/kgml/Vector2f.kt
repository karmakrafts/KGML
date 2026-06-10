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

@Suppress("NOTHING_TO_INLINE")
data class Vector2f( // @formatter:off
    @JvmField val x: Float,
    @JvmField val y: Float
) : VectorNf { // @formatter:on
    companion object : VectorType {
        override val componentType: KClass<*> = Float::class
        override val dimensions: Int = 2
    }

    constructor(xy: Float) : this(xy, xy)

    override val type: VectorType get() = Vector2f

    inline operator fun plus(xy: Float): Vector2f = Vector2f(x + xy, y + xy)
    inline operator fun plus(other: Vector2f): Vector2f = Vector2f(x + other.x, y + other.y)

    inline operator fun minus(xy: Float): Vector2f = Vector2f(x - xy, y - xy)
    inline operator fun minus(other: Vector2f): Vector2f = Vector2f(x - other.x, y - other.y)

    inline operator fun times(xy: Float): Vector2f = Vector2f(x * xy, y * xy)
    inline operator fun times(other: Vector2f): Vector2f = Vector2f(x * other.x, y * other.y)

    inline operator fun div(xy: Float): Vector2f = Vector2f(x / xy, y / xy)
    inline operator fun div(other: Vector2f): Vector2f = Vector2f(x / other.x, y / other.y)

    inline operator fun rem(xy: Float): Vector2f = Vector2f(x % xy, y % xy)
    inline operator fun rem(other: Vector2f): Vector2f = Vector2f(x % other.x, y % other.y)
}