package com.mahmutalperenunal.scorebook.ui.scoreboard.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.domain.model.PlayerModel
import com.mahmutalperenunal.scorebook.domain.model.ScoreModel

class ScoreRowAdapter : RecyclerView.Adapter<ScoreRowAdapter.ScoreRowViewHolder>() {

    private var rounds: Int = 0
    private var players: List<PlayerModel> = emptyList()
    private var scores: List<ScoreModel> = emptyList()

    fun submitScores(rounds: Int, players: List<PlayerModel>, scores: List<ScoreModel>) {
        this.rounds = rounds
        this.players = players
        this.scores = scores
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreRowViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_score_row, parent, false)
        return ScoreRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreRowViewHolder, position: Int) {
        holder.bind(position + 1, players, scores)
    }

    override fun getItemCount(): Int = rounds

    class ScoreRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val handNumber = itemView.findViewById<TextView>(R.id.tv_hand_number)
        private val llScores = itemView.findViewById<LinearLayout>(R.id.ll_scores)

        fun bind(round: Int, players: List<PlayerModel>, scores: List<ScoreModel>) {
            handNumber.text = round.toString()
            llScores.removeAllViews()

            players.forEach { player ->
                val score = scores.find { it.round == round && it.playerId == player.id }
                val scoreView = TextView(itemView.context).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(80.dp, ViewGroup.LayoutParams.WRAP_CONTENT)
                    gravity = Gravity.CENTER
                    text = score?.score?.toString() ?: "-"
                    setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyMedium)
                }
                llScores.addView(scoreView)
            }
        }

        private val Int.dp: Int get() = (this * itemView.resources.displayMetrics.density).toInt()
    }
}