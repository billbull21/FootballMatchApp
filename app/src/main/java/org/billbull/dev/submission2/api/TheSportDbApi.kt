package org.billbull.dev.submission2.api

import org.billbull.dev.submission2.BuildConfig

object TheSportDbApi {
    fun getLeague(id: String?): String {
        return BuildConfig.BASE_URL+"api/v1/json/1/lookupleague.php?id=$id"
    }
    fun getPrevMatch(id: String?): String {
        return BuildConfig.BASE_URL+"api/v1/json/1/eventspastleague.php?id=$id"
    }
    fun getNextMatch(id: String?): String {
        return BuildConfig.BASE_URL+"api/v1/json/1/eventsnextleague.php?id=$id"
    }
    fun getDetailMatch(id: String?): String {
        return BuildConfig.BASE_URL+"api/v1/json/1/lookupevent.php?id=$id"
    }
    fun searchMatch(query: String?): String {
        return BuildConfig.BASE_URL+"api/v1/json/1/searchevents.php?e=$query"
    }
    fun getDetailTeam(id: String?): String {
        return BuildConfig.BASE_URL+"api/v1/json/1/lookupteam.php?id=$id"
    }
}