package com.simon.swipequiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.question_item.view.*

class QuestionAdapter(private val questions: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.question_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question : Question) {
            itemView.tvQuestion.setText(question.text)

            itemView.tvQuestion.setOnClickListener {
                val snackbar = Snackbar.make(
                    itemView.rootView.findViewById(R.id.rootConstraint),
                    "The correct answer is: " + question.answer,
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }
        }
    }

    fun removeItem(position: Int) {
        questions.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, questions.size)
    }

    fun addItem(question: Question) {
        questions.add(Question(question.text, question.answer))
        notifyDataSetChanged()
    }
}