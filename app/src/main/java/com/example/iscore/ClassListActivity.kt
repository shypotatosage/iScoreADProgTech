package com.example.iscore

import Adapter.ClassListRVAdapter
import Adapter.ListDataRVAdapter
import Database.GlobalVar
import Interface.CardListener
import Model.Classroom
import Model.Student
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_class_list.*
import kotlinx.android.synthetic.main.classlist_card.*

class ClassListActivity : AppCompatActivity(), CardListener {

    private var classArrayList: ArrayList<Classroom> = arrayListOf()
    private lateinit var adapter: ClassListRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_list)

        setAdapter()
        getData()
        listener()
    }

    override fun onResume() {
        super.onResume()

        setAdapter()
        getData()
    }

    private fun listener() {
        addclassFAB.setOnClickListener {
            val intent = Intent(this,AddClassActivity::class.java)
            startActivity(intent)
        }

        classListFAB.setOnClickListener {
            finish()
        }
    }

    private fun getData() {
        val database = Firebase.database
        val ref = database.getReference("users").child("users").child(GlobalVar.user.id).child("classes")

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (classSnapshot in snapshot.children) {
                        var className = classSnapshot.child("name").getValue()
                        var classDesc = classSnapshot.child("desc").getValue()
                        var classID = classSnapshot.child("id").getValue()
                        var imageUri = classSnapshot.child("imageUri").getValue()

                        var i = 0
                        var students = arrayListOf<Student>()

                        while (i < classSnapshot.child("students").childrenCount) {
                            i++

                            students.add(Student("a", "a", arrayListOf(), "a", "a"))
                        }

                        var classroom = Classroom(classID.toString(),
                            className.toString(), classDesc.toString(), students)

                        classroom.imageUri = imageUri.toString()

                        classArrayList.add(classroom!!)
                    }

                    GlobalVar.classArrayList = classArrayList
                    classArrayList = arrayListOf()

                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Data", error.getMessage()) //Don't ignore errors!
            }

        })
    }

    private fun setAdapter() {
        adapter = ClassListRVAdapter(classArrayList, this)
        classList_recyclerView.layoutManager = LinearLayoutManager(this)
        classList_recyclerView.adapter = adapter
    }

    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, StudentListActivity::class.java).apply {
            putExtra("Position", position)
        }

        startActivity(myIntent)
    }
}