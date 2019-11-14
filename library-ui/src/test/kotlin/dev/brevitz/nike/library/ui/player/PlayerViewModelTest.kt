package dev.brevitz.nike.library.ui.player

import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.core.domain.RemoteError
import dev.brevitz.nike.library.domain.player.Player
import dev.brevitz.nike.library.domain.player.PlayerRepository
import dev.brevitz.nike.library.domain.player.generator.PlayerGen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class PlayerViewModelTest : FunSpec() {
    private val repository = mockk<PlayerRepository>()
    private val playerGen = PlayerGen()

    private val viewModel = PlayerViewModel(repository, Schedulers.trampoline(), Schedulers.trampoline())

    init {
        context("ViewModel data functions") {
            test("getSubtitle") {
                playerGen.forAll { player: Player ->
                    viewModel.getSubtitle(player) == "#${player.jerseyNumber} | ${player.position}"
                }
            }

            test("getCaptionOrRookie") {
                playerGen.forAll { player: Player ->
                    viewModel.getCaptionOrRookie(player) == when {
                        player.captain -> "Captain"
                        player.alternateCaptain -> "Alternate Captain"
                        player.rookie -> "Rookie"
                        else -> null
                    }
                }
            }

            test("getShootCatch") {
                playerGen.forAll { player: Player ->
                    viewModel.getShootCatch(player) == if (player.position.equals("Goalie", true)) {
                        "Catches: ${player.shootsCatches}"
                    } else {
                        "Shoots: ${player.shootsCatches}"
                    }
                }
            }
        }

        context("ViewModel State updates") {
            test("Repository is loading") {
                every { repository.getDetails(any()) } returns Observable.just(RemoteData.Loading)

                viewModel.start(0)
                viewModel.observe()
                    .map { it.player }
                    .test()
                    .assertValue(RemoteData.Loading)
                    .dispose()
            }

            test("Repository is success") {
                val player = playerGen.random().first()
                every { repository.getDetails(player.id) } returns Observable.just(RemoteData.Success(player))

                viewModel.start(player.id)
                viewModel.observe()
                    .map { it.player }
                    .test()
                    .assertValue(RemoteData.Success(player))
                    .dispose()
            }

            test("Repository is error") {
                every { repository.getDetails(any()) } returns Observable.just(RemoteData.Error(RemoteError.HttpError(401, "Nah son")))

                viewModel.start(0)
                viewModel.observe()
                    .map { it.player }
                    .test()
                    .assertValue(RemoteData.Error(RemoteError.HttpError(401, "Nah son")))
                    .dispose()
            }
        }
    }
}
