package org.billbull.dev.submission2.model

data class Favorite(val id: String?, val homeName: String?, val homeScore: String?, val awayName: String?, val awayScore: String?, val date: String?) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val EVENT_DATE: String = "EVENT_DATE"
    }
}