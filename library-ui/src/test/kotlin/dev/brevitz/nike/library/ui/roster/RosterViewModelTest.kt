package dev.brevitz.nike.library.ui.roster

import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.core.domain.RemoteError
import dev.brevitz.nike.library.domain.roster.RosterRepository
import dev.brevitz.nike.library.domain.roster.generator.RosterGen
import io.kotlintest.specs.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class RosterViewModelTest : FunSpec() {
    private val repository = mockk<RosterRepository>()
    private val viewModel = RosterViewModel(repository, Schedulers.trampoline(), Schedulers.trampoline())

    init {
        test("Repository is loading") {
            every { repository.getRoster(any()) } returns Observable.just(RemoteData.Loading)

            viewModel.getRoster(17)
            viewModel.observe()
                .map { it.roster }
                .test()
                .assertValue(RemoteData.Loading)
                .dispose()
        }

        test("Repository is success") {
            val roster = RosterGen().random().first()
            every { repository.getRoster(any()) } returns Observable.just(RemoteData.Success(roster))

            viewModel.getRoster(17)
            viewModel.observe()
                .map { it.roster }
                .test()
                .assertValue(RemoteData.Success(roster))
                .dispose()
        }

        test("Repository is error") {
            every { repository.getRoster(any()) } returns Observable.just(RemoteData.Error(RemoteError.HttpError(401, "Nah son")))

            viewModel.getRoster(17)
            viewModel.observe()
                .map { it.roster }
                .test()
                .assertValue(RemoteData.Error(RemoteError.HttpError(401, "Nah son")))
                .dispose()
        }
    }
}
