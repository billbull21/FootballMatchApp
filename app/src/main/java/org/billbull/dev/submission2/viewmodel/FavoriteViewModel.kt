package org.billbull.dev.submission2.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.billbull.dev.submission2.api.BaseApiRepository
import org.billbull.dev.submission2.model.Favorite
import org.billbull.dev.submission2.model.LeagueItem
import org.jetbrains.anko.doAsync
import org.billbull.dev.submission2.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteViewModel : ViewModel() {

    private var mutableList: MutableLiveData<MutableList<Favorite>?> = MutableLiveData()
    private var dataList: MutableList<Favorite> = mutableListOf()

    fun setData(ctx: Context) {
        ctx.database.use {
            val data = select(Favorite.TABLE_FAVORITE)
            val result = data.parseList(classParser<Favorite>())
            dataList.addAll(result)
            mutableList.postValue(dataList)
        }
    }

    fun observeData(): LiveData<MutableList<Favorite>?> {
        return mutableList
    }

}