package dev.brevitz.nike.library.ui.roster

import dev.brevitz.nike.core.data.di.ObserveThread
import dev.brevitz.nike.core.data.di.SubscribeThread
import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.core.domain.RemoteError
import dev.brevitz.nike.core.ui.ViewModel
import dev.brevitz.nike.library.domain.roster.Roster
import dev.brevitz.nike.library.domain.roster.RosterRepository
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

internal class RosterViewModel @Inject constructor(
    private val repository: RosterRepository,
    @ObserveThread private val observeThread: Scheduler,
    @SubscribeThread private val subscribeThread: Scheduler
) : ViewModel<RosterViewModel.State>(State()) {

    fun start() {
        repository.getRoster(TEAM_ID)
            .observeOn(observeThread)
            .subscribeOn(subscribeThread)
            .doOnNext {
                when (it) {
                    is RemoteData.Error -> Timber.e(it.toString())
                }
            }
            .doOnError { Timber.e(it) }
            .subscribe(
                { data -> updateState { it.copy(roster = data) } },
                { error -> updateState { it.copy(roster = RemoteData.error(RemoteError.ParsingError(error))) } }
            )
            .addTo(disposables)
    }

    data class State(
        val roster: RemoteData<Roster, RemoteError> = RemoteData.NotAsked
    )

    private companion object {
        private const val TEAM_ID = 17
    }
}
