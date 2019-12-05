package org.billbull.dev.submission2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import org.billbull.dev.submission2.model.EventX
import org.billbull.dev.submission2.view.MatchDetail
import org.jetbrains.anko.startActivity

class SearchAdapter(private val context: Context, private val events: List<EventX>) : RecyclerView.Adapter<SearchAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder
            = EventsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.home_name.text = events[position].strHomeTeam
        holder.home_score.text = events[position].intHomeScore
        holder.away_name.text = events[position].strAwayTeam
        holder.away_score.text = events[position].intAwayScore
        holder.date.text = events[position].dateEvent
        holder.itemView.setOnClickListener {
            context.startActivity<MatchDetail>("data" to events[position].idEvent, "name" to events[position].strEvent)
        }
    }

    class EventsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val home_score = itemView.home_score
        val home_name = itemView.home_team
        val away_score = itemView.away_score
        val away_name = itemView.away_team
        val date = itemView.match_date
    }

}