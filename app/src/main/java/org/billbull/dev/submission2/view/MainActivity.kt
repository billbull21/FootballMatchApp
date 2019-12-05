package org.billbull.dev.submission2.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.billbull.dev.submission2.*
import org.billbull.dev.submission2.model.EventX
import org.billbull.dev.submission2.model.League
import org.billbull.dev.submission2.viewmodel.SearchViewModel
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var viewModel: SearchViewModel
    private var filterString: String? = null
    private val KEYWORD = "keyword"

    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        viewModel = ViewModelProviders.of(this)[SearchViewModel::class.java]
        viewModel.observeData().observe(this, observeData)

        if (savedInstanceState != null) {
            filterString = savedInstanceState.getString(KEYWORD)
            if (filterString == null) {
                viewModel.observeData().removeObserver(observeData)
                rv_league.layoutManager = LinearLayoutManager(this)
                rv_league.adapter = LeagueAdapter(this, leagues)
                rv_league?.adapter?.notifyDataSetChanged()
            }
        } else {
            viewModel.observeData().removeObserver(observeData)
            rv_league.layoutManager = LinearLayoutManager(this)
            rv_league.adapter = LeagueAdapter(this, leagues)
            rv_league?.adapter?.notifyDataSetChanged()
        }

        bottom_navigation.selectedItemId = R.id.teams
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.teams -> {
                    startActivity<MainActivity>()
                    finish()
                }
                R.id.favorites -> {
                    startActivity<FavoriteActivity>()
                    finish()
                }
            }
            true
        }

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Football Match"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (filterString != null) {
            outState.putString(KEYWORD, filterString)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        if (filterString != null && !filterString!!.trim().isEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(filterString, true)
        }

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                filterString = null
                initData()
                progressBar.gone()
                rv_league.layoutManager = LinearLayoutManager(context)
                rv_league.adapter = LeagueAdapter(context, leagues)
                rv_league?.adapter?.notifyDataSetChanged()
                return true
            }

        })

        searchQuery(searchView)
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchQuery(searchView: SearchView?) {
        searchView?.queryHint = "Find Some Match..."
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.observeData().removeObserver(observeData)
                viewModel.observeData().observe(this@MainActivity, observeData)
                viewModel.setData(query)
                leagues.clear()
                rv_league?.adapter?.notifyDataSetChanged()
                filterString = query
                progressBar.visible()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun initData() {
        val idData = resources.getStringArray(R.array.league_id)
        val nameData = resources.getStringArray(R.array.league_name)
        val iconData = resources.obtainTypedArray(R.array.league_image)
        leagues.clear()
        for (i in nameData.indices) {
            leagues.add(
                League(
                    idData[i],
                    nameData[i],
                    iconData.getResourceId(i, 0)
                )
            )
        }

        // recycle typed array
        iconData.recycle()
    }

    private val observeData: Observer<MutableList<EventX>?> = Observer {
        progressBar.gone()
        if (it == null) {
            tvKosong.visible()
        } else {
            tvKosong.gone()
            rv_league.layoutManager = LinearLayoutManager(context)
            rv_league.adapter = SearchAdapter(context, it)
            rv_league?.adapter?.notifyDataSetChanged()
        }
    }

}
