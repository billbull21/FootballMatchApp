package org.billbull.dev.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.billbull.dev.submission2.api.BaseApiRepository
import org.billbull.dev.submission2.api.TheSportDbApi
import org.billbull.dev.submission2.model.Event
import org.billbull.dev.submission2.model.EventX
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class SearchViewModel : ViewModel() {
    private val mutableList: MutableLiveData<MutableList<EventX>?> = MutableLiveData()
    private var dataList: MutableList<EventX>? = mutableListOf()

    fun setData(query: String?) {
        val request = BaseApiRepository()
        val gson = Gson()

        doAsyncResult {
            val data = gson.fromJson(request.doRequest(TheSportDbApi.searchMatch(query)), Event::class.java)
            Log.e("DATA", data.event.toString())
            uiThread {
                dataList?.clear()
                dataList = mutableListOf()
                if (data?.event != null) {
                    Log.e("DATA1", data.event.toString())
                    for (i in data.event.indices) {
                        Log.e("DATA2", data.event[i].strSport)
                        if (data.event[i].strSport == "Soccer") {
                            val datax = EventX(
                                data.event[i].dateEvent,
                                data.event[i].idAwayTeam,
                                data.event[i].idEvent,
                                data.event[i].idHomeTeam,
                                data.event[i].idLeague,
                                data.event[i].intAwayScore,
                                data.event[i].intHomeScore,
                                data.event[i].strAwayTeam,
                                data.event[i].strEvent,
                                data.event[i].strHomeTeam,
                                data.event[i].strLeague,
                                data.event[i].strSport
                            )
                            Log.e("DATA2.2", datax.toString())
                            dataList?.add(datax)
                            mutableList.postValue(dataList)
                        } else {
                            dataList = null
                            mutableList.postValue(dataList)
                        }
                    }
                } else {
                    dataList = null
                    mutableList.postValue(dataList)
                }
            }
        }
    }

    fun observeData() : LiveData<MutableList<EventX>?> {
        return mutableList
    }

}