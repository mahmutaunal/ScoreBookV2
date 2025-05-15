package com.mahmutalperenunal.scorebook.ui.scoreboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.domain.model.PlayerTotalScoreModel

class PlayerTotalAdapter : ListAdapter<PlayerTotalScoreModel, PlayerTotalAdapter.PlayerTotalViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerTotalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player_total, parent, false)
        return PlayerTotalViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerTotalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlayerTotalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: PlayerTotalScoreModel) {
            itemView.findViewById<TextView>(R.id.tv_player_total).text = item.totalScore.toString()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlayerTotalScoreModel>() {
        override fun areItemsTheSame(oldItem: PlayerTotalScoreModel, newItem: PlayerTotalScoreModel) = oldItem.playerId == newItem.playerId
        override fun areContentsTheSame(oldItem: PlayerTotalScoreModel, newItem: PlayerTotalScoreModel) = oldItem == newItem
    }
}