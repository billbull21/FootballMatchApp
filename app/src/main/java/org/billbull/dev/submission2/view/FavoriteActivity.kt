package org.billbull.dev.submission2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.toolbar.*
import org.billbull.dev.submission2.FavEventsAdapter
import org.billbull.dev.submission2.R
import org.billbull.dev.submission2.gone
import org.billbull.dev.submission2.model.Favorite
import org.billbull.dev.submission2.viewmodel.FavoriteViewModel
import org.billbull.dev.submission2.visible
import org.jetbrains.anko.startActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        viewModel = ViewModelProviders.of(this)[FavoriteViewModel::class.java]
        viewModel.observeData().observe(this, observeData)

        rv_fav.layoutManager = LinearLayoutManager(applicationContext)
        rv_fav?.setHasFixedSize(true)
        rv_fav?.adapter?.notifyDataSetChanged()

        bottom_navigation.selectedItemId = R.id.favorites
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
        viewModel.setData(this)
        progressBar.visible()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Football Match Favorite"
    }

    val observeData: Observer<MutableList<Favorite>?> = Observer {
        progressBar.gone()
        if (it != null) {
            if (it.size > 0) {
                rv_fav.adapter = it.toMutableList().let { it1 -> FavEventsAdapter(this, it1) }
            } else {
                tvKosong.visible()
            }
        }
    }

}
