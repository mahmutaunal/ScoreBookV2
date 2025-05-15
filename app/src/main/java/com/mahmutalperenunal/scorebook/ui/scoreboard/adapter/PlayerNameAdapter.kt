package com.mahmutalperenunal.scorebook.ui.scoreboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.domain.model.PlayerModel

class PlayerNameAdapter : ListAdapter<PlayerModel, PlayerNameAdapter.PlayerNameViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerNameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player_name, parent, false)
        return PlayerNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerNameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlayerNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(player: PlayerModel) {
            itemView.findViewById<TextView>(R.id.tv_player_name).text = player.name
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlayerModel>() {
        override fun areItemsTheSame(oldItem: PlayerModel, newItem: PlayerModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PlayerModel, newItem: PlayerModel) = oldItem == newItem
    }
}