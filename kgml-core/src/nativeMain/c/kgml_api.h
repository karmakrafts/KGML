#pragma once

#include <stdint.h>
#include <stddef.h>

#define KGML_EXPORT __attribute__((visibility("default")))

#ifdef __cplusplus
#define KGML_API_BEGIN extern "C" {
#define KGML_API_END };
#else
#define KGML_API_BEGIN
#define KGML_API_END
#endif

#define KGML_DEFINE_VEC_TYPE(t, d) \
    typedef t __kgml_##t##d  __attribute__((vector_size(sizeof(t) * d))); \
    typedef struct kgml_##t##d { \
        __kgml_##t##d value; \
    } kgml_##t##d##_t;