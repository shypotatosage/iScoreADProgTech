package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.android.synthetic.main.activity_student_list.*

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
    }

    private fun listener() {
        addStudentBackFAB.setOnClickListener {
            val intent = Intent(this,StudentListActivity::class.java)
            startActivity(intent)
        }

        addStudentBtn.setOnClickListener {  }
    }
}