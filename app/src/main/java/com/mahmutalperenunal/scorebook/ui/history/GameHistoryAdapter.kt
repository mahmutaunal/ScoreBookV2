package com.mahmutalperenunal.scorebook.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.databinding.ItemGameHistoryBinding
import com.mahmutalperenunal.scorebook.domain.model.GameSummaryModel
import java.text.SimpleDateFormat
import java.util.*

class GameHistoryAdapter(
    private val onGameClick: (GameSummaryModel) -> Unit,
    private val onToggleFavorite: (GameSummaryModel) -> Unit
) : RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder>() {

    private val items = mutableListOf<GameSummaryModel>()

    fun submitList(newList: List<GameSummaryModel>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGameHistoryBinding.inflate(inflater, parent, false)
        return GameHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameHistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class GameHistoryViewHolder(
        private val binding: ItemGameHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: GameSummaryModel) = with(binding) {
            tvGameName.text = model.game.name
            tvGameType.text = "${model.game.subGameType.mainType.displayName} - ${model.game.subGameType.displayName}"
            tvGameStatus.text = if (model.game.hasEnded) itemView.context.getString(R.string.completed) else itemView.context.getString(R.string.in_progress)

            val formatter = SimpleDateFormat("d MMM yyyy", Locale("tr"))
            tvGameDate.text = formatter.format(Date(model.game.createdAt))

            ivFavorite.setImageResource(
                if (model.game.isFavorite) R.drawable.ic_favourite else R.drawable.ic_favourite
            )

            root.setOnClickListener {
                onGameClick(model)
            }

            ivFavorite.setOnClickListener {
                onToggleFavorite(model)
            }
        }
    }
}
