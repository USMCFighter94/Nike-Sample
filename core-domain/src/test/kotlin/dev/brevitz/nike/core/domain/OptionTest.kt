package dev.brevitz.nike.core.domain

import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class OptionTest : DescribeSpec() {
    init {
        describe("Option Functions") {
            context("map") {
                it("Option.Some") {
                    Option.Some(Person("Colin")).map { it.name } shouldBe Option.Some("Colin")
                }

                it("Option.None") {
                    Option.None.map<Person> { it } shouldBe Option.None
                }
            }

            context("flatMap") {
                it("Option.Some") {
                    Option.Some(Person("Colin")).flatMap { Option.Some(it.name) } shouldBe Option.Some("Colin")
                }

                it("Option.None") {
                    Option.None.flatMap<Person> { it } shouldBe Option.None
                }
            }

            context("filter") {
                it("Option.Some") {
                    Option.Some(Person("Colin")).filter { it.name == "Colin" } shouldBe Option.Some(Person("Colin"))
                }

                it("Option.None") {
                    Option.None.filter { it } shouldBe Option.None
                }
            }
        }

        describe("Option Extensions") {
            context("toOption") {
                it("Null Object") {
                    null.toOption<String>() shouldBe Option.None
                }

                it("Non Null Object") {
                    "This isn't null".toOption() shouldBe Option.Some("This isn't null")
                }
            }

            context("orNull") {
                it("is Some") {
                    Option.Some("Some object").orNull() shouldBe "Some object"
                }

                it("is None") {
                    Option.None.orNull() shouldBe null
                }
            }

            context("toRemoteData") {
                context("Using Default Empty Value") {
                    it("is Some") {
                        Option.Some("Some object")
                            .toRemoteData<String, RemoteError>() shouldBe RemoteData.Success("Some object")

                    }

                    it("is None") {
                        Option.None.toRemoteData<String, RemoteError>() shouldBe RemoteData.Loading
                    }
                }

                context("Using RemoteData.NotAsked for Empty Value") {
                    it("is Some") {
                        Option.Some("Some object")
                            .toRemoteData<String, RemoteError>(RemoteData.NotAsked) shouldBe RemoteData.Success("Some object")

                    }

                    it("is None") {
                        Option.None.toRemoteData<String, RemoteError>(RemoteData.NotAsked) shouldBe RemoteData.NotAsked
                    }
                }
            }
        }
    }

    data class Person(val name: String)
}
