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

@Suppress("NOTHING_TO_INLINE")
class MatrixStack<M : MatrixNxN>( // @formatter:off
    initialSize: Int = 8,
    @PublishedApi internal val identityProvider: () -> M
) { // @formatter:on
    @PublishedApi
    internal val matrices: ArrayList<M> = ArrayList(initialSize)

    inline fun swap(matrix: M) {
        matrices[matrices.lastIndex] = matrix
    }

    inline fun current(): M = matrices.last()

    inline fun push(matrix: M = identityProvider()) {
        matrices += matrix
    }

    inline fun pop(): M? = matrices.removeLastOrNull()

    @Suppress("UNCHECKED_CAST")
    inline fun reduce(reducer: (M, M) -> M = { a, b -> (a * b) as M }): M = matrices.reduce(reducer)
}