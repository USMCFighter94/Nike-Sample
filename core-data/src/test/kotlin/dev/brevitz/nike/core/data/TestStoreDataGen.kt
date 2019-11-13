package dev.brevitz.nike.core.data

import io.kotlintest.properties.Gen

class TestStoreDataGen : Gen<TestStoreData> {
    override fun constants() = emptyList<TestStoreData>()

    override fun random() = generateSequence { TestStoreData(Gen.string().random().first()) }
}
