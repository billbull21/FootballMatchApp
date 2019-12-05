package org.billbull.dev.submission2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import org.billbull.dev.submission2.model.EventK
import org.billbull.dev.submission2.model.Favorite
import org.billbull.dev.submission2.view.MatchDetail
import org.jetbrains.anko.startActivity

class FavEventsAdapter(private val context: Context, private val events: List<Favorite>) : RecyclerView.Adapter<FavEventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder
            = EventsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.home_name.text = events[position].homeName
        holder.home_score.text = events[position].homeScore
        holder.away_name.text = events[position].awayName
        holder.away_score.text = events[position].awayScore
        holder.date.text = events[position].date
        holder.itemView.setOnClickListener {
            context.startActivity<MatchDetail>("data" to events[position].id, "name" to events[position].homeName + events[position].awayName)
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