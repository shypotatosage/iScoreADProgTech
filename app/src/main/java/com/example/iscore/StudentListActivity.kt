package com.example.iscore

import Adapter.ClassListRVAdapter
import Adapter.StudentListRVAdapter
import Database.GlobalVar
import Database.GlobalVar.Companion.classArrayList
import Interface.CardListener
import Model.Classroom
import Model.Score
import Model.Student
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_class_list.*
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_student_list.*

class StudentListActivity : AppCompatActivity(), CardListener {

    private var classPosition: Int = -1
    private var studentArrayList: ArrayList<Student> = arrayListOf()
    private lateinit var adapter: StudentListRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        GetIntent()
        setAdapter()
        getData()
        listener()
    }

    override fun onResume() {
        super.onResume()

        setAdapter()
        getData()
    }

    private fun GetIntent() {
        classPosition  = intent.getIntExtra("Position", -1)
    }

    private fun listener() {
        studentListFAB.setOnClickListener {
            finish()
        }

        editclassFAB.setOnClickListener {
            val intent = Intent(this,EditClassActivity::class.java).apply {
                putExtra("Position", classPosition)
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
                val cid = classArrayList[classPosition].id
                myRef.child("users").child(uid).child("classes").child(cid).removeValue()
            }
        }
        addStudentofclassFAB.setOnClickListener {
            val intent = Intent(this,AddStudentActivity::class.java).apply {
                putExtra("Position", classPosition)
            }

            startActivity(intent)
        }
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

                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Data", error.getMessage()) //Don't ignore errors!
            }

        })
    }

    private fun setAdapter() {
        adapter = StudentListRVAdapter(studentArrayList, this)
        studentList_recyclerView.layoutManager = LinearLayoutManager(this)
        studentList_recyclerView.adapter = adapter
    }

    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, StudentListActivity::class.java).apply {
            putExtra("Class Position", classPosition)
            putExtra("Student Position", position)
        }

        startActivity(myIntent)
    }
}