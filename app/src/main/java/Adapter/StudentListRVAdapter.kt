package Adapter

import Interface.CardListener
import Model.Classroom
import Model.Student
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iscore.R
import kotlinx.android.synthetic.main.card_studentlist.view.*
import kotlinx.android.synthetic.main.classlist_card.view.*

class StudentListRVAdapter(val listStudent: ArrayList<Student>, val cardListener: CardListener):
    RecyclerView.Adapter<StudentListRVAdapter.viewHolder>(){
    class viewHolder (itemView: View, val cardListener1: CardListener): RecyclerView.ViewHolder(itemView){

        fun setData(data: Student){
            itemView.studentNameTV.text = data.name
            itemView.studentAddressTV.text = data.address
            itemView.studentPhoneTV.text = data.phoneNumber

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
        val view = layoutInflater.inflate(R.layout.card_studentlist, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listStudent[position])
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }

}