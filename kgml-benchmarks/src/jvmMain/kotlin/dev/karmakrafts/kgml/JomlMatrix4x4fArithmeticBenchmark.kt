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
import org.joml.Matrix4f

@State(Scope.Benchmark)
open class JomlMatrix4x4fArithmeticBenchmark {
    val genericLeft = Matrix4f().set(
        1.25F,
        2.5F,
        3.75F,
        4.25F,
        5.5F,
        6.75F,
        7.25F,
        8.5F,
        9.75F,
        10.25F,
        11.5F,
        12.75F,
        13.25F,
        14.5F,
        15.75F,
        16.25F
    )
    val genericRight = Matrix4f().set(
        16.25F,
        15.75F,
        14.5F,
        13.25F,
        12.75F,
        11.5F,
        10.25F,
        9.75F,
        8.5F,
        7.25F,
        6.75F,
        5.5F,
        4.25F,
        3.75F,
        2.5F,
        1.25F
    )
    val genericScale = Matrix4f().set(
        4F, 0F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 0F, 6F, 0F, 0F, 0F, 0F, 1F
    )
    val genericScaleRight = Matrix4f().set(
        7F, 0F, 0F, 0F, 0F, 8F, 0F, 0F, 0F, 0F, 9F, 0F, 0F, 0F, 0F, 1F
    )
    val translation = Matrix4f().translation(2F, 3F, 4F)
    val translationRight = Matrix4f().translation(5F, 7F, 11F)
    val scale = Matrix4f().scaling(4F, 5F, 6F)
    val scaleRight = Matrix4f().scaling(7F, 8F, 9F)
    val rotation = Matrix4f().rotationZ(Math.toRadians(45.0).toFloat())
    val rotationRight = Matrix4f().rotationZ(Math.toRadians(30.0).toFloat())
    val genericRotation = Matrix4f(rotation)
    val genericRotationRight = Matrix4f(rotationRight)
    val perspective = Matrix4f().perspective(70F, 1920F / 1080F, 0.1F, 100F)

    @JvmName("generic")
    @Benchmark
    fun generic(): Matrix4f {
        return genericLeft.mul(genericRight, Matrix4f())
    }

    @JvmName("genericDiagonalDiagonal")
    @Benchmark
    fun genericDiagonalDiagonal(): Matrix4f {
        return genericScale.mul(genericScaleRight, Matrix4f())
    }

    @JvmName("genericRotationRotation")
    @Benchmark
    fun genericRotationRotation(): Matrix4f {
        return genericRotation.mul(genericRotationRight, Matrix4f())
    }

    @JvmName("linearDiagonal")
    @Benchmark
    fun linearDiagonal(): Matrix4f {
        return rotation.mul(scale, Matrix4f())
    }

    @JvmName("diagonalLinear")
    @Benchmark
    fun diagonalLinear(): Matrix4f {
        return scale.mul(rotation, Matrix4f())
    }

    @JvmName("diagonalDiagonal")
    @Benchmark
    fun diagonalDiagonal(): Matrix4f {
        return scale.mul(scaleRight, Matrix4f())
    }

    @JvmName("rotationRotation")
    @Benchmark
    fun rotationRotation(): Matrix4f {
        return rotation.mul(rotationRight, Matrix4f())
    }

    @JvmName("affineLinear")
    @Benchmark
    fun affineLinear(): Matrix4f {
        return translation.mul(scale, Matrix4f())
    }

    @JvmName("linearTranslation")
    @Benchmark
    fun linearTranslation(): Matrix4f {
        return scale.mul(translation, Matrix4f())
    }

    @JvmName("translationTranslation")
    @Benchmark
    fun translationTranslation(): Matrix4f {
        return translation.mul(translationRight, Matrix4f())
    }

    @JvmName("perspectiveLinear")
    @Benchmark
    fun perspectiveLinear(): Matrix4f {
        return perspective.mul(rotation, Matrix4f())
    }
}