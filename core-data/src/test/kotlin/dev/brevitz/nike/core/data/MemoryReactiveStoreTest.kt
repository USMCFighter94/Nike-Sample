package dev.brevitz.nike.core.data

import dev.brevitz.nike.core.domain.Option
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import java.util.concurrent.ConcurrentHashMap

class MemoryReactiveStoreTest : FunSpec() {
    private val storeMap = ConcurrentHashMap<MemoryStoreKey, TestStoreData>()
    private val storeDataGen = TestStoreDataGen()

    private val memoryReactiveStore = MemoryReactiveStore<MemoryStoreKey, TestStoreData>(storeMap) { MemoryStoreKey }

    init {
        test("Item is saved in memory store") {
            val itemToStore = storeDataGen.random().first()

            memoryReactiveStore.store(itemToStore)

            storeMap[MemoryStoreKey] shouldBe itemToStore
        }

        test("Item saved is emitted to observers") {
            val itemToStore = storeDataGen.random().first()

            storeMap[MemoryStoreKey] = itemToStore

            memoryReactiveStore.get(MemoryStoreKey)
                .test()
                .assertValue(Option.Some(itemToStore))

        }

        test("Item saved is emitted to observers as they're added") {
            val itemToStore = storeDataGen.random().first()

           memoryReactiveStore.get(MemoryStoreKey)
                .test()
                .assertValues(Option.None, Option.Some(itemToStore))

            storeMap[MemoryStoreKey] = itemToStore
        }
    }

    private object MemoryStoreKey
}
