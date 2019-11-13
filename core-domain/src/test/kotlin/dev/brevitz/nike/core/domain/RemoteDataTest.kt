package dev.brevitz.nike.core.domain

import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.reactivex.Maybe

class RemoteDataTest : DescribeSpec() {
    init {
        describe("Remote Data Extensions") {
            context("toMaybeError") {
                it("RemoteData.NotAsked") {
                    RemoteData.NotAsked.toMaybeError() shouldBe Maybe.empty()
                }

                it("RemoteData.Loading") {
                    RemoteData.Loading.toMaybeError() shouldBe Maybe.empty()
                }

                it("RemoteData.Success") {
                    RemoteData.Success("").toMaybeError() shouldBe Maybe.empty()
                }

                it("RemoteData.Error") {
                    RemoteData.Error("There was an error").toMaybeError()
                        .test()
                        .assertValue("There was an error")
                }
            }
        }
    }
}
