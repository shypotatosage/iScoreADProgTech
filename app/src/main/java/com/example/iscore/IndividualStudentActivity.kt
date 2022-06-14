package com.example.iscore

import Database.GlobalVar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_individual_student.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class IndividualStudentActivity : AppCompatActivity() {

    private var classPosition: Int = -1
    private var studentPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_student)

        GetIntent()
        setData()
        listener()
    }

    private fun GetIntent() {
        classPosition  = intent.getIntExtra("Class Position", -1)
        studentPosition  = intent.getIntExtra("Student Position", -1)
    }

    private fun setData() {
        namaindividualstudent_textView.text = GlobalVar.classArrayList[classPosition].students[studentPosition].name
        alamatindividualstudent_textView.text = GlobalVar.classArrayList[classPosition].students[studentPosition].address
        notelpindividualstudent_textView.text = GlobalVar.classArrayList[classPosition].students[studentPosition].phoneNumber
    }

    private fun listener() {
        individualStudentFAB.setOnClickListener {
            finish()
        }

        editstudent_button.setOnClickListener {
            val intent = Intent(this,EditStudentActivity::class.java).apply {
                putExtra("Class Position", classPosition)
                putExtra("Student Position", studentPosition)
            }

            startActivity(intent)
        }

        checkscore_button.setOnClickListener {
            val intent = Intent(this,ViewScoresActivity::class.java).apply {
                putExtra("Class Position", classPosition)
                putExtra("Student Position", studentPosition)
            }

            startActivity(intent)
        }

        deletestudent_button.setOnClickListener {
            val database = Firebase.database
            val ref = database.getReference("users").child("users").child(GlobalVar.user.id).child("classes").child(GlobalVar.classArrayList[classPosition].id).child("students")
        }

        addscore_button.setOnClickListener {
            val intent = Intent(this,AddScoreActivity::class.java).apply {
                putExtra("Class Position", classPosition)
                putExtra("Student Position", studentPosition)
            }

            startActivity(intent)
        }
    }
}