package dev.brevitz.nike.library.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.core.ui.load
import dev.brevitz.nike.library.domain.player.Player
import dev.brevitz.nike.library.ui.DaggerContainer
import dev.brevitz.nike.library.ui.R
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.view_player_detail.*
import javax.inject.Inject

class PlayerDetailDialogFragment : DialogFragment() {
    @Inject
    internal lateinit var viewModel: PlayerViewModel

    private var playerId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)

        DaggerContainer.coreComponent.componentManager()
            .getOrCreate(PlayerComponent.KEY) {
                DaggerPlayerComponent.builder()
                    .coreComponent(DaggerContainer.coreComponent)
                    .build()
            }
            .inject(this)

        playerId = arguments?.getLong(ID_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.view_player_detail, container, false)

    override fun onResume() {
        super.onResume()
        viewModel.observe()
            .map { it.player }
            .distinctUntilChanged()
            .doOnNext { playerDetailLoadingView.isVisible = it.isLoading() }
            .subscribe {
                when (it) {
                    is RemoteData.Success -> setData(it.data)
                    is RemoteData.Error -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.error_dialog_title)
                            .setMessage(R.string.error_dialog_message)
                            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
            }
            .addTo(viewModel.disposables)

        playerId?.let {
            viewModel.start(it)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
    }

    private fun setData(player: Player) {
        playerImage.load(player.image)
        playerName.text = player.fullName
        playerSubtitle.text = viewModel.getSubtitle(player)
        playerTeam.text = player.currentTeam.name

        val captainRookieText = viewModel.getCaptionOrRookie(player)

        with(playerCaptainRookie) {
            playerCaptainRookie.text = captainRookieText
            isVisible = captainRookieText != null
        }

        playerShoots.text = viewModel.getShootCatch(player)
    }

    companion object {
        private const val ID_KEY = "PlayerId"

        fun newInstance(id: Long) = PlayerDetailDialogFragment().apply {
            arguments = bundleOf(ID_KEY to id)
        }
    }
}
