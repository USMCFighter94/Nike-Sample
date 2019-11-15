package dev.brevitz.nike.library.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isVisible
import dev.brevitz.nike.library.ui.location.LocationUpdates
import dev.brevitz.nike.library.ui.location.LocationView
import dev.brevitz.nike.library.ui.roster.RosterView

class TeamView : LinearLayout {

    private var rosterView: RosterView? = null
    private var locationView: LocationView? = null
    private var title: TextView? = null
    private var listView: ListView? = null

    private val locationUpdater = object : LocationUpdates {
        override fun locationUpdated(city: String, state: String) {
            val teams = teamMap[city].orEmpty() + teamMap[state].orEmpty()

            title?.isVisible = teams.isNotEmpty()
            listView?.isVisible = teams.isNotEmpty()

            if (teams.isNotEmpty()) {
                listView?.let { view ->
                    view.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, teams.map { it.name }.distinct())
                    view.setOnItemClickListener { parent, _, position, _ ->
                        val name = parent.getItemAtPosition(position)

                        teams.find { it.name == name }?.let {
                            rosterView?.setTeamId(it.id)
                            locationView?.stopLocationUpdates()
                        }
                    }
                }
            }
        }
    }

    init {
        inflate(context, R.layout.view_team, this)

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL

        rosterView = findViewById(R.id.rosterView)
        locationView = findViewById(R.id.locationView)
        title = findViewById(R.id.teamTitle)
        listView = findViewById(R.id.teamListView)

        locationView?.locationUpdates = locationUpdater
        rosterView?.setTeamId(DEFAULT_TEAM_ID)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private companion object {
        private const val DEFAULT_TEAM_ID = 17
    }
}
