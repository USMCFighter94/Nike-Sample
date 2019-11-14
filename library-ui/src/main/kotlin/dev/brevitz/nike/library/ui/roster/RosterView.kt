package dev.brevitz.nike.library.ui.roster

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.library.ui.DaggerContainer
import dev.brevitz.nike.library.ui.R
import dev.brevitz.nike.library.ui.player.PlayerDetailDialogFragment
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.view_roster.view.*
import javax.inject.Inject

class RosterView : FrameLayout {
    @Inject
    internal lateinit var viewModel: RosterViewModel

    private val controller = RosterPlayerController { playerId ->
        (context as? FragmentActivity?)?.supportFragmentManager?.let {
            PlayerDetailDialogFragment.newInstance(playerId)
                .show(it, PlayerDetailDialogFragment::class.java.simpleName)
        }
    }

    init {
        DaggerContainer.coreComponent.componentManager()
            .getOrCreate(RosterComponent.KEY) {
                DaggerRosterComponent.builder()
                    .coreComponent(DaggerContainer.coreComponent)
                    .build()
            }
            .inject(this)

        inflate(context, R.layout.view_roster, this)

        with(rosterViewPager) {
            adapter = controller.adapter
            offscreenPageLimit = 3
            setPageTransformer(RosterPagerTransformer(resources))
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.start()

        viewModel.observe()
            .map { it.roster }
            .distinctUntilChanged()
            .doOnNext { rosterLoadingView.isVisible = it.isLoading() }
            .subscribe {
                when (it) {
                    is RemoteData.Success -> controller.setData(it.data.players)
                    is RemoteData.Error -> {
                        AlertDialog.Builder(context)
                            .setTitle(R.string.error_dialog_title)
                            .setMessage(R.string.error_dialog_message)
                            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
            }
            .addTo(viewModel.disposables)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewModel.stop()
    }
}
