package org.billbull.dev.submission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.billbull.dev.submission2.api.BaseApiRepository
import org.billbull.dev.submission2.api.TheSportDbApi
import org.billbull.dev.submission2.model.LeagueItem
import org.billbull.dev.submission2.model.Leagues
import org.billbull.dev.submission2.model.Team
import org.billbull.dev.submission2.model.Teams
import org.jetbrains.anko.doAsync

class DetailViewModel : ViewModel() {

    private var mutableList: MutableLiveData<MutableList<Team>?> = MutableLiveData()
    private var mutableList2: MutableLiveData<MutableList<Team>?> = MutableLiveData()

    fun setData(id: String?, type: String?) {
        val request = BaseApiRepository()
        val gson = Gson()

        doAsync {
            val data = gson.fromJson(request.doRequest(TheSportDbApi.getDetailTeam(id)), Teams::class.java)
            if(type == "home") {
                mutableList.postValue(data.teams.toMutableList())
            } else {
                mutableList2.postValue(data.teams.toMutableList())
            }
        }
    }

    fun observeData(): LiveData<MutableList<Team>?> {
        return mutableList
    }

    fun observeData2(): LiveData<MutableList<Team>?> {
        return mutableList2
    }

}