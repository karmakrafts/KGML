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

import dev.karmakrafts.kgml.vector.Vector3f
import kotlin.test.Test

class SourceVector3fExtensionsTest {
    @Test
    fun `readVector3f should read big endian vector`() = assertReads(
        bytesOf(1F, -2F, 3.5F), Vector3f(1F, -2F, 3.5F)
    ) { readVector3f() }

    @Test
    fun `readVector3fLe should read little endian vector`() = assertReads(
        bytesOf(1F, -2F, 3.5F, littleEndian = true), Vector3f(1F, -2F, 3.5F)
    ) { readVector3fLe() }
}