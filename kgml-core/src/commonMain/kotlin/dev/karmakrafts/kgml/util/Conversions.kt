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

package dev.karmakrafts.kgml.util

import kotlin.math.PI

@PublishedApi
internal const val TO_RAD: Double = PI / 180.0

@PublishedApi
internal const val TO_DEG: Double = 180.0 / PI

/**
 * Convert the given value from degrees into radians.
 * Internally uses 64-bit types to reduce loss of precision.
 *
 * @param value the value in degrees to convert.
 * @return The converted value in radians.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun toDegrees(value: Float): Float = (value * TO_DEG).toFloat()

/**
 * Convert the given value from degrees into radians.
 *
 * @param value the value in degrees to convert.
 * @return The converted value in radians.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun toDegrees(value: Double): Double = value * TO_DEG

/**
 * Convert the given value from radians into degrees.
 * Internally uses 64-bit types to reduce loss of precision.
 *
 * @param value the value in radians to convert.
 * @return The converted value in degrees.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun toRadians(value: Float): Float = (value * TO_RAD).toFloat()

/**
 * Convert the given value from radians into degrees.
 *
 * @param value the value in radians to convert.
 * @return The converted value in degrees.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun toRadians(value: Double): Double = value * TO_RAD