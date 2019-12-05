package org.billbull.dev.submission2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_league_detail.*
import org.billbull.dev.submission2.R
import org.billbull.dev.submission2.model.League
import org.billbull.dev.submission2.view.fragment.NextMatchFragment
import org.billbull.dev.submission2.view.fragment.PreviousMatchFragment


class LeagueDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)

        val data = intent.getParcelableExtra<League>("data")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = data?.name
        toolbar.setNavigationOnClickListener { finish() }

        tabs.setupWithViewPager(viewpager)
        setupViewPager(viewpager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PreviousMatchFragment(), "LAST MATCH")
        adapter.addFragment(NextMatchFragment(), "NEXT MATCH")
        viewPager.adapter = adapter
    }

    class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val mFragment: MutableList<Fragment> = mutableListOf()
        private val mFragmentTitle: MutableList<String> = mutableListOf()

        fun addFragment(fragment: Fragment, title: String) {
            mFragment.add(fragment)
            mFragmentTitle.add(title)
        }

        override fun getItem(position: Int): Fragment = mFragment[position]

        override fun getCount(): Int = mFragment.size

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitle[position]
        }

    }

}
