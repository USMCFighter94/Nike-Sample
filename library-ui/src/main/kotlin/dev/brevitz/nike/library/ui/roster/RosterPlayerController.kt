package dev.brevitz.nike.library.ui.roster

import com.airbnb.epoxy.TypedEpoxyController
import dev.brevitz.nike.library.domain.roster.RosterPlayer

internal class RosterPlayerController : TypedEpoxyController<List<RosterPlayer>>() {
    override fun buildModels(data: List<RosterPlayer>) {
        data.forEach {
            RosterPlayerModel(it)
                .id(it.id)
                .addTo(this)
        }
    }
}
