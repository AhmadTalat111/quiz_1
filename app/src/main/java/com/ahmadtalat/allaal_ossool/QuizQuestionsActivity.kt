package com.ahmadtalat.allaal_ossool

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

//To do -> confirm button works even if no choice is selected use an
// if() to stop from going to the next question


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var currentPosition = 1
    private var questionsList: ArrayList<Question>? = null
    private var selectedOptionPosition = 0
    private var score: Int = 0
    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        userName = intent.getStringExtra(Constants.USER_NAME)

        questionsList = Constants.getQuestion()
        setQuestion()
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)


    }
    private fun setQuestion(){

        val question = questionsList!![currentPosition - 1]
        defaultOptionsView()
        if (currentPosition == questionsList!!.size){
            btn_submit.text = "إنهاء المسابقة"
        }else{
            btn_submit.text = "تأكيد"
        }
        progressBar.progress = currentPosition
        tv_progress.text = "$currentPosition" + "/" + "${progressBar.max}"

        tv_question.text = question.question
        tv_option_one.text =question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one -> selectedOptionView(tv_option_one, 1)
            R.id.tv_option_two -> selectedOptionView(tv_option_two, 2)
            R.id.tv_option_three -> selectedOptionView(tv_option_three, 3)
            R.id.tv_option_four -> selectedOptionView(tv_option_four, 4)
            R.id.btn_submit -> {
                if (selectedOptionPosition == 0) {
                        currentPosition++
                    when {
                        currentPosition <= questionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, Result::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, score)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, questionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = questionsList?.get(currentPosition - 1)
                    if (question!!.correctAnswer != selectedOptionPosition) {
                       score = score
                    }
                    else {
                        score++
                    }
                    if (currentPosition == questionsList!!.size){
                        btn_submit.text = "إنهاء المسابقة"

                    }else{
                        btn_submit.text = "السؤال التالي"
                    }
                    selectedOptionPosition = 0
                }
            }
        }
    }
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        selectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}