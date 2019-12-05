package org.billbull.dev.submission2.view.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_previous_match.*
import org.billbull.dev.submission2.*

import org.billbull.dev.submission2.model.EventK
import org.billbull.dev.submission2.model.League
import org.billbull.dev.submission2.model.LeagueItem
import org.billbull.dev.submission2.viewmodel.EventsViewModel
import org.billbull.dev.submission2.viewmodel.LeagueViewModel


class PreviousMatchFragment() : Fragment() {

    private lateinit var viewModel: EventsViewModel
    private lateinit var viewModel2: LeagueViewModel
    private var data: League? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_previous_match, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this)[EventsViewModel::class.java]
        viewModel.observeData().observe(this, observeData)
        viewModel2 = ViewModelProviders.of(this)[LeagueViewModel::class.java]
        viewModel2.observeData().observe(this, observeData2)

        data = activity?.intent?.getParcelableExtra("data")

        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelable("data")
        }

        rv_matchList.layoutManager = LinearLayoutManager(activity?.applicationContext)
        rv_matchList?.setHasFixedSize(true)
        rv_matchList?.adapter?.notifyDataSetChanged()

        viewModel.setData(data?.id, "")
        viewModel2.setData(data?.id)
        progressBar.visible()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("data", data)
        super.onSaveInstanceState(outState)
    }

    private val observeData: Observer<MutableList<EventK>?> = Observer {
        progressBar.gone()
        rv_matchList.adapter = it?.let { it1 -> EventsAdapter(activity!!, it1) }
    }

    private val observeData2: Observer<MutableList<LeagueItem>?> = Observer {
        activity?.let { it1 ->
            Glide.with(it1)
                .load(it?.get(0)?.strLogo)
                .into(imv_iconLeague)
        }
        tv_nameDesc.text = it?.get(0)?.strDescriptionEN
    }

}
