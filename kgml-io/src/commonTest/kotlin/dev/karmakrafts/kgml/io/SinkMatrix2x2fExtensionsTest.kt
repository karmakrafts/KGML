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

package dev.karmakrafts.kgml.io

import dev.karmakrafts.kgml.matrix.Matrix2x2f
import kotlin.test.Test

class SinkMatrix2x2fExtensionsTest {
    @Test
    fun `writeMatrix2x2f should write big endian matrix`() = assertWrites(
        bytesOf(1F, 2F, 3F, 4F)
    ) { writeMatrix2x2f(Matrix2x2f(1F, 2F, 3F, 4F)) }

    @Test
    fun `writeMatrix2x2fLe should write little endian matrix`() = assertWrites(
        bytesOf(1F, 2F, 3F, 4F, littleEndian = true)
    ) { writeMatrix2x2fLe(Matrix2x2f(1F, 2F, 3F, 4F)) }
}