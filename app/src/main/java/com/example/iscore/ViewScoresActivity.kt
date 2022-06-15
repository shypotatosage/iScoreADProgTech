package com.example.iscore

import Adapter.StudentListRVAdapter
import Adapter.ViewScoresRVAdapter
import Database.GlobalVar
import Interface.CardListener
import Model.Score
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
import kotlinx.android.synthetic.main.activity_student_list.*
import kotlinx.android.synthetic.main.activity_view_scores.*

class ViewScoresActivity : AppCompatActivity(), CardListener {

    private var classPosition: Int = -1
    private var studentPosition: Int = -1
    private lateinit var scoreArrayList: ArrayList<Score>
    private lateinit var adapter: ViewScoresRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_scores)

        scoreArrayList = arrayListOf()
        GetIntent()
        setAdapter()
        getData()
        listener()
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        scoreArrayList = arrayListOf()
        GetIntent()
        setAdapter()
        getData2()
        listener()
        adapter.notifyDataSetChanged()
    }

    private fun GetIntent() {
        classPosition  = intent.getIntExtra("Class Position", -1)
        studentPosition  = intent.getIntExtra("Student Position", -1)
    }

    private fun listener() {
        viewScoresBackFAB.setOnClickListener {
            finish()
        }
    }

    private fun getData() {
        val database = Firebase.database
        val ref = database.getReference("users").child("users").child(GlobalVar.user.id).child("classes").child(
            GlobalVar.classArrayList[classPosition].id).child("students").child(GlobalVar.classArrayList[classPosition].students[studentPosition].id).child("scores")

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (classSnapshot in snapshot.children) {
                        var scoreName = classSnapshot.child("name").getValue()
                        var scoreNote = classSnapshot.child("note").getValue()
                        var scoreID = classSnapshot.child("id").getValue()
                        var scoreValue = classSnapshot.child("value").getValue()

                        var score = Score(scoreID.toString(), scoreName.toString(),
                            scoreValue.toString().toInt(), scoreNote.toString())

                        scoreArrayList.add(score!!)
                    }

                    GlobalVar.classArrayList[classPosition].students[studentPosition].scores = scoreArrayList

                    Toast.makeText(applicationContext, "" + scoreArrayList.size, Toast.LENGTH_SHORT).show()
                    scoreArrayList = arrayListOf()
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Data", error.getMessage()) //Don't ignore errors!
            }

        })
    }

    private fun getData2() {
        val database = Firebase.database
        val ref = database.getReference("users").child("users").child(GlobalVar.user.id).child("classes").child(
            GlobalVar.classArrayList[classPosition].id).child("students").child(GlobalVar.classArrayList[classPosition].students[studentPosition].id).child("scores")

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (classSnapshot in snapshot.children) {
                        var scoreName = classSnapshot.child("name").getValue()
                        var scoreNote = classSnapshot.child("note").getValue()
                        var scoreID = classSnapshot.child("id").getValue()
                        var scoreValue = classSnapshot.child("value").getValue()

                        var score = Score(scoreID.toString(), scoreName.toString(),
                            scoreValue.toString().toInt(), scoreNote.toString())

                        scoreArrayList.add(score!!)
                    }

                    GlobalVar.classArrayList[classPosition].students[studentPosition].scores = scoreArrayList

                    adapter.notifyDataSetChanged()ith
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Data", error.getMessage()) //Don't ignore errors!
            }

        })
    }

    private fun setAdapter() {
        adapter = ViewScoresRVAdapter(scoreArrayList, this)
        viewScore_recyclerView.layoutManager = LinearLayoutManager(this)
        viewScore_recyclerView.adapter = adapter
    }

    override fun onCardClick(position: Int) {
        val myIntent = Intent(this, EditScoreActivity::class.java).apply {
            putExtra("Class Position", classPosition)
            putExtra("Student Position", studentPosition)
            putExtra("Score Position", position)
        }

        startActivity(myIntent)
    }
}