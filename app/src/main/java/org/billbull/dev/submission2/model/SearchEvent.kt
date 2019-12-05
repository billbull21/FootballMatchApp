package org.billbull.dev.submission2.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class Event(
    @SerializedName("event")
    val event: List<EventX>
)

@Parcelize
data class EventX(
    @SerializedName("dateEvent")
    val dateEvent: String?,
    @SerializedName("idAwayTeam")
    val idAwayTeam: String?,
    @SerializedName("idEvent")
    val idEvent: String?,
    @SerializedName("idHomeTeam")
    val idHomeTeam: String?,
    @SerializedName("idLeague")
    val idLeague: String?,
    @SerializedName("intAwayScore")
    val intAwayScore: String?,
    @SerializedName("intHomeScore")
    val intHomeScore: String?,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String?,
    @SerializedName("strEvent")
    val strEvent: String?,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String?,
    @SerializedName("strLeague")
    val strLeague: String?,
    @SerializedName("strSport")
    val strSport: String?
) : Parcelable