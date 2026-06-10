#pragma once

#include "kgml_api.h"

KGML_API_BEGIN

KGML_DEFINE_VEC_TYPE(float, 4)

KGML_EXPORT static inline kgml_float4_t kgml_float4_create(float x, float y, float z) {
    kgml_float4_t result = {};
    const __kgml_float4 value = {x, y, z};
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