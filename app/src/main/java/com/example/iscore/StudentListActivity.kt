package com.example.iscore

import Database.GlobalVar.Companion.classArrayList
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

        editclassFAB.setOnClickListener {
            val intent = Intent(this,EditClassActivity::class.java).apply {
                putExtra("Position", position)
            }

            startActivity(intent)
        }
        deleteclassFAB.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = user.uid
                val database = Firebase.database
                val myRef = database.getReference("users")
                val cid = classArrayList[position].id
                myRef.child("users").child(uid).child("classes").child(cid).removeValue()
            }
        }
        addStudentofclassFAB.setOnClickListener {
            val intent = Intent(this,AddStudentActivity::class.java).apply {
                putExtra("Position", position)
            }

            startActivity(intent)
        }
    }
}