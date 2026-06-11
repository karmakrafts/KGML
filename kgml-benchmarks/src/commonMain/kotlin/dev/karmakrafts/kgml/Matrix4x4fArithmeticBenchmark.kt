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

import dev.karmakrafts.kgml.matrix.Matrix4x4f
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName

@State(Scope.Benchmark)
open class Matrix4x4fArithmeticBenchmark {
    @JvmName("run")
    @Benchmark
    fun run(): Matrix4x4f {
        val m1 = Matrix4x4f()
        val m2 = Matrix4x4f( // @formatter:off
            0F, 0F, 0F, 1F,
            0F, 0F, 1F, 0F,
            0F, 1F, 0F, 0F,
            1F, 0F, 0F, 0F
        ) // @formatter:on
        return m1 * m2
    }
}