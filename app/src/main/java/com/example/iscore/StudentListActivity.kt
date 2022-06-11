package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_class_list.*
import kotlinx.android.synthetic.main.activity_student_list.*

class StudentListActivity : AppCompatActivity() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        GetIntent()
        listener()
    }

    private fun GetIntent() {
        position  = intent.getIntExtra("Position", -1)
    }

    private fun listener() {
        studentListFAB.setOnClickListener {
            finish()
        }

        addStudentofclassFAB.setOnClickListener {
            val intent = Intent(this,AddStudentActivity::class.java).apply {
                putExtra("Position", position)
            }

            startActivity(intent)
        }
    }
}