package com.example.iscore

import Database.GlobalVar
import Model.Score
import Model.Student
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_individual_student.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class IndividualStudentActivity : AppCompatActivity() {

    private var classPosition: Int = -1
    private var studentPosition: Int = -1
    private var studentArrayList: ArrayList<Student> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_student)

        GetIntent()
        getData()
        setData()
        listener()
    }

    override fun onResume() {
        super.onResume()

        getData()
        listener()
    }

    private fun GetIntent() {
        classPosition  = intent.getIntExtra("Class Position", -1)
        studentPosition  = intent.getIntExtra("Student Position", -1)
    }

    private fun getData() {
        val database = Firebase.database
        val ref = database.getReference("users").child("users").child(GlobalVar.user.id).child("classes").child(GlobalVar.classArrayList[classPosition].id).child("students")

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (classSnapshot in snapshot.children) {
                        var studentName = classSnapshot.child("name").getValue()
                        var studentAddress = classSnapshot.child("address").getValue()
                        var studentID = classSnapshot.child("id").getValue()
                        var studentNumber = classSnapshot.child("phoneNumber").getValue()

                        var scores = arrayListOf<Score>()

                        var student = Student(studentID.toString(), studentName.toString(), scores, studentAddress.toString(), studentNumber.toString())

                        studentArrayList.add(student!!)
                    }

                    GlobalVar.classArrayList[classPosition].students = studentArrayList
                    studentArrayList = arrayListOf()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Data", error.getMessage()) //Don't ignore errors!
            }

        })
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

            ref.orderByChild("id").equalTo(GlobalVar.classArrayList[classPosition].students[studentPosition].id).addListenerForSingleValueEvent(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (classSnapshot in snapshot.children) {
                            classSnapshot.ref.removeValue()
                        }

                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Data", error.getMessage()) //Don't ignore errors!
                }
            })


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