package dev.brevitz.nike.library.ui.roster

import android.content.Intent
import dev.brevitz.nike.library.ui.BaseUiTest
import dev.brevitz.nike.library.ui.MockActivity
import dev.brevitz.nike.library.ui.test.R
import org.junit.Before
import org.junit.Test

class RosterViewTest : BaseUiTest() {
    @Before
    override fun setup() {
        super.setup()
        MockActivity.layout = R.layout.test_view_roster
    }

    @Test
    fun tappingRosterItem_loadsDetailView() {
        restartActivity()

        roster {
            clickRosterItem()
            detailViewIsShown()
        }
    }

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }

        activityRule.launchActivity(Intent())
        activityRule.activity.findViewById<RosterView>(R.id.testRosterView).setTeamId(17)
    }
}
