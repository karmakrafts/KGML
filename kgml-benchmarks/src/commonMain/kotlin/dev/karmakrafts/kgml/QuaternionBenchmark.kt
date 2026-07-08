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

import dev.karmakrafts.kgml.transform.Quaternion
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName

@State(Scope.Benchmark)
open class QuaternionBenchmark {
    val identity = Quaternion.identity
    val left = Quaternion.fromAngles(30F, 45F, 60F)
    val right = Quaternion.fromAngles(15F, 25F, 35F)
    val closeRight = Quaternion.fromAngles(30.01F, 45.01F, 60.01F)

    @JvmName("fromAngles")
    @Benchmark
    fun fromAngles(): Quaternion {
        return Quaternion.fromAngles(30F, 45F, 60F)
    }

    @JvmName("fromAnglesRad")
    @Benchmark
    fun fromAnglesRad(): Quaternion {
        return Quaternion.fromAnglesRad(0.5235988F, 0.7853982F, 1.0471976F)
    }

    @JvmName("multiply")
    @Benchmark
    fun multiply(): Quaternion {
        return left * right
    }

    @JvmName("multiplyIdentity")
    @Benchmark
    fun multiplyIdentity(): Quaternion {
        return left * identity
    }

    @JvmName("multiplyScalar")
    @Benchmark
    fun multiplyScalar(): Quaternion {
        return left * 0.5F
    }

    @JvmName("slerp")
    @Benchmark
    fun slerp(): Quaternion {
        return left.slerp(right, 0.35F)
    }

    @JvmName("slerpClose")
    @Benchmark
    fun slerpClose(): Quaternion {
        return left.slerp(closeRight, 0.35F)
    }

    @JvmName("getAngles")
    @Benchmark
    fun getAngles(): Float {
        return left.getAngleX() + left.getAngleY() + left.getAngleZ()
    }

    @JvmName("toRotationMatrix3x3")
    @Benchmark
    fun toRotationMatrix3x3() = left.toRotationMatrix3x3()

    @JvmName("toRotationMatrix4x4")
    @Benchmark
    fun toRotationMatrix4x4() = left.toRotationMatrix4x4()
}