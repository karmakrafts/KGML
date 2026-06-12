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

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import org.joml.Vector4f

@State(Scope.Benchmark)
open class JomlVector4fArithmeticBenchmark {
    @JvmName("run")
    @Benchmark
    fun run(): Vector4f {
        val v1 = Vector4f(1F, 2F, 4F, 8F)
        val v2 = Vector4f(4F, 8F, 16F, 32F)
        return v1.mul(v2)
    }
}