package com.example.iscore

import Adapter.ClassListRVAdapter
import Adapter.ListDataRVAdapter
import Database.GlobalVar
import Interface.CardListener
import Model.Classroom
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_class_list.*
import kotlinx.android.synthetic.main.activity_register.*

class ClassListActivity : AppCompatActivity(), CardListener {

    private var classArrayList: ArrayList<Classroom> = arrayListOf()
    private val adapter = ClassListRVAdapter(classArrayList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_list)

        setAdapter()
        getData()
        listener()
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
                        val classroom = classSnapshot.getValue(Classroom::class.java)

                        classArrayList.add(classroom!!)
                    }

                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        )
    }

    private fun setAdapter() {
        classList_recyclerView.layoutManager = LinearLayoutManager(this)
        classList_recyclerView.adapter = adapter
    }

    override fun onCardClick(position: Int) {
        TODO("Not yet implemented")
    }
}