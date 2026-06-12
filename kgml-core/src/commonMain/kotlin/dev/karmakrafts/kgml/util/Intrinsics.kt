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

/**
 * Computes a fused multiply-add operation: `(a * b) + c`.
 *
 * @param a The first operand.
 * @param b The second operand.
 * @param c The third operand.
 * @return The result of `(a * b) + c`.
 */
expect inline fun fma(a: Float, b: Float, c: Float): Float

/**
 * Computes a fused multiply-add operation: `(a * b) + c`.
 *
 * @param a The first operand.
 * @param b The second operand.
 * @param c The third operand.
 * @return The result of `(a * b) + c`.
 */
expect inline fun fma(a: Double, b: Double, c: Double): Double

/**
 * Computes a fused multiply-add operation: `(a * b) + c`.
 *
 * @param a The first operand.
 * @param b The second operand.
 * @param c The third operand.
 * @return The result of `(a * b) + c`.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun fma(a: Int, b: Int, c: Int): Int = fma(a.toFloat(), b.toFloat(), c.toFloat()).toInt()

/**
 * Computes a fused multiply-add operation: `(a * b) + c`.
 *
 * @param a The first operand.
 * @param b The second operand.
 * @param c The third operand.
 * @return The result of `(a * b) + c`.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun fma(a: Long, b: Long, c: Long): Long = fma(a.toDouble(), b.toDouble(), c.toDouble()).toLong()