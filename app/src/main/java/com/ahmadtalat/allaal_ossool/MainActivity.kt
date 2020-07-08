package com.ahmadtalat.allaal_ossool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        btn_start.setOnClickListener {
            if (edit_text_e0.text.toString().isEmpty()){
                Toast.makeText(this, "فضلاً أدخل رقمك الوظيفي", Toast.LENGTH_SHORT).show()
            }else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, edit_text_e0.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}