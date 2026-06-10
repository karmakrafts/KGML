// Copyright 2026 Karma Krafts
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#pragma once

#include "kgml_api.h"

KGML_API_BEGIN

KGML_DEFINE_VEC_TYPE(float, 4)

KGML_EXPORT static inline kgml_float4_t kgml_float4_create(float x, float y, float z, float w) {
    kgml_float4_t result = {};
    const __kgml_float4 value = {x, y, z, w};
    result.value = value;
    return result;
}

KGML_EXPORT static inline float kgml_float4_get_x(const kgml_float4_t vector) {
    return vector.value[0];
}

KGML_EXPORT static inline void kgml_float4_set_x(kgml_float4_t* vector, float x) {
    vector->value[0] = x;
}

KGML_EXPORT static inline float kgml_float4_get_y(const kgml_float4_t vector) {
    return vector.value[1];
}

KGML_EXPORT static inline void kgml_float4_set_y(kgml_float4_t* vector, float y) {
    vector->value[1] = y;
}

KGML_EXPORT static inline float kgml_float4_get_z(const kgml_float4_t vector) {
    return vector.value[2];
}

KGML_EXPORT static inline void kgml_float4_set_z(kgml_float4_t* vector, float z) {
    vector->value[2] = z;
}

KGML_EXPORT static inline float kgml_float4_get_w(const kgml_float4_t vector) {
    return vector.value[3];
}

KGML_EXPORT static inline void kgml_float4_set_w(kgml_float4_t* vector, float w) {
    vector->value[3] = w;
}

// Arithmetic

KGML_EXPORT static inline kgml_float4_t kgml_float4_add(const kgml_float4_t a, const kgml_float4_t b) {
    kgml_float4_t result = {};
    const __kgml_float4 value = a.value + b.value;
    result.value = value;
    return result;
}

KGML_EXPORT static inline kgml_float4_t kgml_float4_sub(const kgml_float4_t a, const kgml_float4_t b) {
    kgml_float4_t result = {};
    const __kgml_float4 value = a.value - b.value;
    result.value = value;
    return result;
}

KGML_EXPORT static inline kgml_float4_t kgml_float4_mul(const kgml_float4_t a, const kgml_float4_t b) {
    kgml_float4_t result = {};
    const __kgml_float4 value = a.value * b.value;
    result.value = value;
    return result;
}

KGML_EXPORT static inline kgml_float4_t kgml_float4_div(const kgml_float4_t a, const kgml_float4_t b) {
    kgml_float4_t result = {};
    const __kgml_float4 value = a.value / b.value;
    result.value = value;
    return result;
}

KGML_API_END