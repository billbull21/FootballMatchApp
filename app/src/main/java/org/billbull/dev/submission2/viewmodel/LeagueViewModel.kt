package org.billbull.dev.submission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.billbull.dev.submission2.api.BaseApiRepository
import org.billbull.dev.submission2.api.TheSportDbApi
import org.billbull.dev.submission2.model.LeagueItem
import org.billbull.dev.submission2.model.Leagues
import org.jetbrains.anko.doAsync

class LeagueViewModel : ViewModel() {

    private var mutableList: MutableLiveData<MutableList<LeagueItem>?> = MutableLiveData()

    fun setData(league: String?) {
        val request = BaseApiRepository()
        val gson = Gson()

        doAsync {
            val data = gson.fromJson(request.doRequest(TheSportDbApi.getLeague(league)), Leagues::class.java)
            mutableList.postValue(data.leagues.toMutableList())
        }
    }

    fun observeData(): LiveData<MutableList<LeagueItem>?> {
        return mutableList
    }

}