package dev.brevitz.nike.library.ui.roster

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyModelWithView
import dev.brevitz.nike.core.ui.inflateAs
import dev.brevitz.nike.core.ui.load
import dev.brevitz.nike.library.domain.roster.RosterPlayer
import dev.brevitz.nike.library.ui.R
import kotlinx.android.synthetic.main.view_roster_player.view.*

internal data class RosterPlayerModel(
    private val player: RosterPlayer,
    private val listener: RosterPlayerClickListener
) : EpoxyModelWithView<ConstraintLayout>() {

    override fun bind(view: ConstraintLayout) {
        super.bind(view)
        with(view) {
            rosterPlayerImage.load(player.image)
            rosterPlayerName.text = player.fullName
            rosterPlayerSubtitle.text = String.format(SUBTITLE, player.jerseyNumber, player.position)
            setOnClickListener { listener(player.id) }
        }
    }

    override fun buildView(parent: ViewGroup): ConstraintLayout = parent.inflateAs(R.layout.view_roster_player)

    private companion object {
        private const val SUBTITLE = "#%s | %s"
    }
}
