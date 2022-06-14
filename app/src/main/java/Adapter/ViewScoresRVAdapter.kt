package Adapter

import Interface.CardListener
import Model.Score
import Model.Student
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iscore.R
import kotlinx.android.synthetic.main.card_studentlist.view.*
import kotlinx.android.synthetic.main.viewscores_card.view.*

class ViewScoresRVAdapter(val listScore: ArrayList<Score>, val cardListener: CardListener):
    RecyclerView.Adapter<ViewScoresRVAdapter.viewHolder>(){
    class viewHolder (itemView: View, val cardListener1: CardListener): RecyclerView.ViewHolder(itemView){

        fun setData(data: Score){
            itemView.scoreNameTV.text = data.name
            itemView.scoreScoreTV.text = data.value.toString()
            if (data.note == "") {
                itemView.scoreNoteTV.text = "(No Notes)"
            } else {
                itemView.scoreNoteTV.text = data.note
            }

            itemView.setOnClickListener {
                cardListener1.onCardClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewscores_card, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listScore[position])
    }

    override fun getItemCount(): Int {
        return listScore.size
    }

}