package dev.brevitz.nike.library.ui.roster

import dev.brevitz.nike.library.ui.R
import dev.brevitz.nike.library.ui.clickViewPagerItem
import dev.brevitz.nike.library.ui.viewIsShown

class RosterRobot {
    fun clickRosterItem() = clickViewPagerItem()

    fun detailViewIsShown() = viewIsShown(R.id.playerDetailLayout)
}

fun roster(f: RosterRobot.() -> Unit) = RosterRobot().apply(f)
