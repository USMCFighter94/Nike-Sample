package dev.brevitz.nike.library.ui.player

import dev.brevitz.nike.core.data.di.ObserveThread
import dev.brevitz.nike.core.data.di.SubscribeThread
import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.core.domain.RemoteError
import dev.brevitz.nike.core.ui.ViewModel
import dev.brevitz.nike.library.domain.player.Player
import dev.brevitz.nike.library.domain.player.PlayerRepository
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

internal class PlayerViewModel @Inject constructor(
    private val repository: PlayerRepository,
    @ObserveThread private val observeThread: Scheduler,
    @SubscribeThread private val subscribeThread: Scheduler
) : ViewModel<PlayerViewModel.State>(State()) {

    fun start(id: Long) {
        repository.getDetails(id)
            .observeOn(observeThread)
            .subscribeOn(subscribeThread)
            .doOnNext {
                when (it) {
                    is RemoteData.Error -> Timber.e(it.toString())
                }
            }
            .doOnError { Timber.e(it) }
            .subscribe(
                { data -> updateState { it.copy(player = data) } },
                { error -> updateState { it.copy(player = RemoteData.error(RemoteError.ParsingError(error))) } }
            )
            .addTo(disposables)
    }

    fun getSubtitle(player: Player) = String.format(SUBTITLE, player.jerseyNumber, player.position)

    fun getCaptionOrRookie(player: Player) = when {
        player.captain -> CAPTAIN
        player.alternateCaptain -> ALT_CAPTAIN
        player.rookie -> ROOKIE
        else -> null
    }

    fun getShootCatch(player: Player) = if (player.position.equals(GOALIE, true)) {
        String.format(CATCHES, player.shootsCatches)
    } else {
        String.format(SHOOTS, player.shootsCatches)
    }

    data class State(
        val player: RemoteData<Player, RemoteError> = RemoteData.NotAsked
    )

    private companion object {
        private const val GOALIE = "Goalie"
        private const val CAPTAIN = "Captain"
        private const val ALT_CAPTAIN = "Alternate Captain"
        private const val ROOKIE = "Rookie"

        private const val SUBTITLE = "#%s | %s"
        private const val SHOOTS = "Shoots: %s"
        private const val CATCHES = "Catches: %s"
    }
}
