package org.billbull.dev.submission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.billbull.dev.submission2.api.BaseApiRepository
import org.billbull.dev.submission2.api.TheSportDbApi
import org.billbull.dev.submission2.model.EventK
import org.billbull.dev.submission2.model.Events
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class EventsViewModel : ViewModel() {

    private val mutableList: MutableLiveData<MutableList<EventK>?> = MutableLiveData()

    fun setData(league: String?, type: String) {
        val request = BaseApiRepository()
        val gson = Gson()

        doAsyncResult {
            when {
                type == "next" -> {
                    val data = gson.fromJson(request.doRequest(TheSportDbApi.getNextMatch(league)), Events::class.java)
                    uiThread {
                        mutableList.postValue(data.events.toMutableList())
                    }
                }
                type == "detail" -> {
                    val data = gson.fromJson(request.doRequest(TheSportDbApi.getDetailMatch(league)), Events::class.java)
                    uiThread {
                        mutableList.postValue(data.events.toMutableList())
                    }
                }
                else -> {
                    val data = gson.fromJson(request.doRequest(TheSportDbApi.getPrevMatch(league)), Events::class.java)
                    uiThread {
                        mutableList.postValue(data.events.toMutableList())
                    }
                }
            }
        }
    }

    fun observeData(): LiveData<MutableList<EventK>?> {
        return mutableList
    }

}