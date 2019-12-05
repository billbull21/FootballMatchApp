package org.billbull.dev.submission2.view

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import org.billbull.dev.submission2.viewmodel.EventsViewModel
import org.billbull.dev.submission2.R
import org.billbull.dev.submission2.db.database
import org.billbull.dev.submission2.model.EventK
import org.billbull.dev.submission2.model.Favorite
import org.billbull.dev.submission2.model.Team
import org.billbull.dev.submission2.viewmodel.DetailViewModel
import org.jetbrains.anko.db.insert

class MatchDetail : AppCompatActivity() {

    private lateinit var viewModel: EventsViewModel
    private lateinit var viewModel2: DetailViewModel
    private lateinit var viewModel3: DetailViewModel

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var id: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        viewModel = ViewModelProviders.of(this)[EventsViewModel::class.java]
        viewModel.observeData().observe(this, observeData)
        viewModel2 = ViewModelProviders.of(this)[DetailViewModel::class.java]
        viewModel2.observeData().observe(this, observeData2)
        viewModel3 = ViewModelProviders.of(this)[DetailViewModel::class.java]
        viewModel3.observeData2().observe(this, observeData3)

        id = intent.getStringExtra("data")
        val matchName = intent.getStringExtra("name")

        Log.e("id", id)

        setSupportActionBar(toolbar)
        supportActionBar?.title = matchName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        viewModel.setData(id, "detail")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_favorite -> {
                addToFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            this.database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to id,
                    Favorite.HOME_NAME to home_team.text,
                    Favorite.HOME_SCORE to home_score.text,
                    Favorite.AWAY_NAME to away_team.text,
                    Favorite.AWAY_SCORE to away_score.text,
                    Favorite.EVENT_DATE to match_date.text
                )
            }
            Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private val observeData: Observer<MutableList<EventK>?> = Observer {
        home_team.text = it?.get(0)?.strHomeTeam
        home_score.text = it?.get(0)?.intHomeScore
        away_team.text = it?.get(0)?.strAwayTeam
        away_score.text = it?.get(0)?.intAwayScore
        match_date.text = it?.get(0)?.dateEvent
        home_scorer.text = it?.get(0)?.strHomeGoalDetails
        away_scorer.text = it?.get(0)?.strAwayGoalDetails
        home_formation.text = it?.get(0)?.strHomeLineupGoalkeeper + " - " + it?.get(0)?.strHomeLineupDefense + " - " + it?.get(0)?.strHomeLineupMidfield + " - " + it?.get(0)?.strHomeLineupForward
        away_formation.text = it?.get(0)?.strAwayLineupGoalkeeper + " - " + it?.get(0)?.strAwayLineupDefense + " - " + it?.get(0)?.strAwayLineupMidfield + " - " + it?.get(0)?.strAwayLineupForward
        viewModel2.setData(it?.get(0)?.idHomeTeam, "home")
        viewModel3.setData(it?.get(0)?.idAwayTeam, "away")
    }

    private val observeData2: Observer<MutableList<Team>?> = Observer {
        Glide.with(this)
            .load(it?.get(0)?.strTeamBadge)
            .into(home_logo)
    }

    private val observeData3: Observer<MutableList<Team>?> = Observer {
        Glide.with(this)
            .load(it?.get(0)?.strTeamBadge)
            .into(away_logo)
    }

}
