package org.billbull.dev.submission2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_league.view.*
import org.billbull.dev.submission2.model.League
import org.billbull.dev.submission2.view.LeagueDetail
import org.jetbrains.anko.startActivity

class LeagueAdapter(private val context: Context, private val leagues: List<League>) : RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_league, parent, false))
    }

    override fun getItemCount(): Int = leagues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = leagues[position].name
        Glide.with(context)
            .load(leagues[position].image)
            .into(holder.image)
        holder.itemView.setOnClickListener {
            context.startActivity<LeagueDetail>("data" to leagues[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.tv_nameLeague
        val image = itemView.imv_iconLeague
    }
}