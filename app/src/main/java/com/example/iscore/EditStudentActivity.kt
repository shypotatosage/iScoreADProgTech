package com.example.iscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_student.*
import kotlinx.android.synthetic.main.activity_individual_student.*

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        editStudentBackFAB.setOnClickListener {
            finish()
        }
    }
}