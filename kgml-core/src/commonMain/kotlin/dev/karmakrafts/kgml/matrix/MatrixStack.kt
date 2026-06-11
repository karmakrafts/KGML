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

/**
 * A stack of matrices for managing transformations.
 *
 * @param M The type of matrix in the stack.
 * @property identityProvider A provider for identity matrices.
 * @param initialSize The initial size of the stack.
 */
@Suppress("NOTHING_TO_INLINE")
class MatrixStack<M : MatrixNxN>( // @formatter:off
    initialSize: Int = 8,
    @PublishedApi internal val identityProvider: () -> M
) { // @formatter:on
    /**
     * The internal list of matrices in the stack.
     */
    @PublishedApi
    internal val matrices: ArrayList<M> = ArrayList(initialSize)

    /**
     * Swaps the current matrix on top of the stack with the given matrix.
     *
     * @param matrix The matrix to swap with.
     */
    inline fun swap(matrix: M) {
        matrices[matrices.lastIndex] = matrix
    }

    /**
     * Gets the current matrix on top of the stack.
     *
     * @return The current matrix.
     */
    inline fun current(): M = matrices.last()

    /**
     * Pushes a new matrix onto the stack.
     *
     * @param matrix The matrix to push. Defaults to an identity matrix from [identityProvider].
     */
    inline fun push(matrix: M = identityProvider()) {
        matrices += matrix
    }

    /**
     * Pops the top matrix from the stack.
     *
     * @return The popped matrix, or null if the stack is empty.
     */
    inline fun pop(): M? = matrices.removeLastOrNull()

    /**
     * Reduces the matrices in the stack using the given reducer.
     *
     * @param reducer The reducer to use. Defaults to matrix multiplication.
     * @return The reduced matrix.
     */
    @Suppress("UNCHECKED_CAST")
    inline fun reduce(reducer: (M, M) -> M = { a, b -> (a * b) as M }): M = matrices.reduce(reducer)
}