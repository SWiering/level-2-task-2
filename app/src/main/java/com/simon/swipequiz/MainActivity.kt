package com.simon.swipequiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var questions = arrayListOf<Question>()
    var questionAdapter: QuestionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        rvQuestions.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvQuestions.adapter = questionAdapter

        for (i in Question.QUESTION_TEXTS.indices) {
            questions.add(Question(Question.QUESTION_TEXTS[i], Question.QUESTION_ANSWERS[i]))
        }

        createItemTouchHelper().attachToRecyclerView(rvQuestions)
        questionAdapter.notifyDataSetChanged()
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedQuestion = questions[position]
                val response: String

                // Remove item if correct, add item back again if incorrect
                questionAdapter.removeItem(position)

                if (direction == ItemTouchHelper.LEFT) {
                    when (deletedQuestion.answer) {
                        true -> {
                            response = getString(R.string.correct_answer, deletedQuestion.answer.toString())
                        }
                        false -> {
                            response = getString(R.string.incorrect_answer)
                            questionAdapter.addItem(deletedQuestion)
                        }
                    }
                } else {
                    when (deletedQuestion.answer) {
                        true -> {
                            response = getString(R.string.incorrect_answer)
                            questionAdapter.addItem(deletedQuestion)
                        }
                        false -> {
                            response = getString(R.string.correct_answer, deletedQuestion.answer.toString())
                        }
                    }
                }

                val snackbar = Snackbar.make(
                    rootConstraint,
                    response,
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }

        }

        return ItemTouchHelper(callback)
    }
}