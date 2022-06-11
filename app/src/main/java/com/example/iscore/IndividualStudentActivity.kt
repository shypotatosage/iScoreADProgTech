package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_individual_student.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class IndividualStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_student)

        individualStudentFAB.setOnClickListener {
            val intent = Intent(this,StudentListActivity::class.java)
            startActivity(intent)
        }

        editstudent_button.setOnClickListener {
            val intent = Intent(this,EditStudentActivity::class.java)
            startActivity(intent)
        }

        checkscore_button.setOnClickListener {
            val intent = Intent(this,ViewScoresActivity::class.java)
            startActivity(intent)
        }

        deletestudent_button.setOnClickListener {
//            val intent = Intent(this,ViewScoresActivity::class.java)
//            startActivity(intent)
        }

        addscore_button.setOnClickListener {
            val intent = Intent(this,AddScoreActivity::class.java)
            startActivity(intent)
        }
    }
}