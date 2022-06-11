package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_individual_student.*
import kotlinx.android.synthetic.main.activity_view_scores.*

class ViewScoresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_scores)

        viewScoresBackFAB.setOnClickListener {
            val intent = Intent(this,IndividualStudentActivity::class.java)
            startActivity(intent)
        }
    }
}