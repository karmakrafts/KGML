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

@file:OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)

package dev.karmakrafts.kgml.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

internal fun assertDescriptor(
    serializer: KSerializer<*>, name: String, kind: PrimitiveKind, elements: Int
) {
    val descriptor = serializer.descriptor
    assertEquals(name, descriptor.serialName)
    assertEquals(StructureKind.LIST, descriptor.kind)
    assertFalse(descriptor.isNullable)
    assertEquals(1, descriptor.elementsCount)
    for (index in 0..elements) {
        assertEquals(index.toString(), descriptor.getElementName(index))
        assertEquals(index, descriptor.getElementIndex(index.toString()))
        assertEquals(kind, descriptor.getElementDescriptor(index).kind)
        assertFalse(descriptor.getElementDescriptor(index).isNullable)
        assertFalse(descriptor.isElementOptional(index))
    }
}

internal fun <T> assertSerializes(serializer: KSerializer<T>, value: T, vararg components: Number) {
    val encoder = ComponentEncoder(serializer.descriptor, components.size)
    serializer.serialize(encoder, value)
    assertEquals(components.mapIndexed { index, component -> index to component }, encoder.elements)
    encoder.assertComplete()
}

internal fun <T> decode(serializer: KSerializer<T>, vararg elements: Pair<Int, Number>): T {
    val decoder = ComponentDecoder(serializer.descriptor, elements.toList())
    val result = serializer.deserialize(decoder)
    decoder.assertComplete()
    if (elements.indices.all { elements[it].first == it }) {
        val sequentialDecoder = ComponentDecoder(serializer.descriptor, elements.toList(), sequential = true)
        assertEquals(result, serializer.deserialize(sequentialDecoder))
        sequentialDecoder.assertComplete()
    }
    return result
}

internal fun <T> assertDeserializes(serializer: KSerializer<T>, expected: T, vararg components: Number) {
    val elements = components.mapIndexed { index, component -> index to component }.reversed()
    assertEquals(expected, decode(serializer, *elements.toTypedArray()))
    val decoder = ComponentDecoder(serializer.descriptor, elements.reversed(), sequential = true)
    assertEquals(expected, serializer.deserialize(decoder))
    decoder.assertComplete()
    for (size in 0 until components.size) {
        val prefix = elements.reversed().take(size)
        val expectedPrefix = decode(serializer, *prefix.toTypedArray())
        val prefixDecoder = ComponentDecoder(serializer.descriptor, prefix, sequential = true)
        assertEquals(expectedPrefix, serializer.deserialize(prefixDecoder))
        prefixDecoder.assertComplete()
    }
}

internal fun <T> assertRoundTrips(serializer: KSerializer<T>, value: T) {
    val encoder = ComponentEncoder(serializer.descriptor)
    serializer.serialize(encoder, value)
    encoder.assertComplete()
    assertEquals(value, decode(serializer, *encoder.elements.toTypedArray()))
}

internal fun assertRejectsUnexpectedIndices(serializer: KSerializer<*>, name: String, size: Int) {
    for (index in listOf(CompositeDecoder.UNKNOWN_NAME, size, size + 1)) {
        val decoder = ComponentDecoder(serializer.descriptor, listOf(index to 0))
        val exception = assertFailsWith<SerializationException> { serializer.deserialize(decoder) }
        assertEquals("Unexpected element index in $name $index", exception.message)
    }
    val zero: Number = when (serializer.descriptor.getElementDescriptor(0).kind) {
        PrimitiveKind.DOUBLE -> 0.0
        PrimitiveKind.FLOAT -> 0F
        else -> 0
    }
    val decoder = ComponentDecoder(serializer.descriptor, List(size + 1) { it to zero }, sequential = true)
    val exception = assertFailsWith<SerializationException> { serializer.deserialize(decoder) }
    assertEquals("Unexpected element index in $name $size", exception.message)
}

private class ComponentEncoder(
    private val expectedDescriptor: SerialDescriptor, private val expectedSize: Int? = null
) : AbstractEncoder() {
    override val serializersModule = EmptySerializersModule()
    val elements = mutableListOf<Pair<Int, Number>>()
    private var beginCount = 0
    private var endCount = 0
    private var currentIndex = -1
    private var collectionSize = -1

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        expectedSize?.let { assertEquals(it, collectionSize) }
        this.collectionSize = collectionSize
        return beginStructure(descriptor)
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        assertSame(expectedDescriptor, descriptor)
        assertEquals(0, beginCount++)
        return this
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        assertSame(expectedDescriptor, descriptor)
        assertEquals(1, beginCount)
        assertEquals(0, endCount)
        assertTrue(index in 0 until collectionSize)
        currentIndex = index
        return true
    }

    override fun encodeValue(value: Any) {
        assertTrue(value is Number)
        elements += currentIndex to value
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        assertSame(expectedDescriptor, descriptor)
        assertEquals(0, endCount++)
    }

    fun assertComplete() {
        assertEquals(1, beginCount, "Encoder must begin the structure")
        assertEquals(1, endCount, "Encoder must end the structure")
        assertEquals(elements.size, collectionSize, "Encoder must declare the array size")
    }
}

private class ComponentDecoder(
    private val expectedDescriptor: SerialDescriptor,
    private val elements: List<Pair<Int, Number>>,
    private val sequential: Boolean = false
) : AbstractDecoder() {
    override val serializersModule = EmptySerializersModule()
    private var beginCount = 0
    private var endCount = 0
    private var position = 0
    private var pendingValue = false

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        assertSame(expectedDescriptor, descriptor)
        assertEquals(0, beginCount++)
        return this
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        assertFalse(sequential, "Sequential decoders do not report element indices")
        assertSame(expectedDescriptor, descriptor)
        assertEquals(1, beginCount)
        assertEquals(0, endCount)
        assertFalse(pendingValue, "Previous component must be decoded")
        if (position == elements.size) return CompositeDecoder.DECODE_DONE
        pendingValue = true
        return elements[position].first
    }

    override fun decodeSequentially(): Boolean = sequential

    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
        assertSame(expectedDescriptor, descriptor)
        return elements.size
    }

    override fun decodeValue(): Any {
        assertTrue(sequential || pendingValue, "Component must be requested before decoding")
        pendingValue = false
        return elements[position++].second
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        assertSame(expectedDescriptor, descriptor)
        assertFalse(pendingValue)
        assertEquals(elements.size, position)
        assertEquals(0, endCount++)
    }

    fun assertComplete() {
        assertEquals(1, beginCount, "Decoder must begin the structure")
        assertEquals(1, endCount, "Decoder must end the structure")
        assertEquals(elements.size, position)
    }
}