package com.example.iscore

import Database.GlobalVar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_score.*

class EditScoreActivity : AppCompatActivity() {

    private var classPosition: Int = -1
    private var studentPosition: Int = -1
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_score)
        GetIntent()
        listener()
    }
    private fun GetIntent() {
        classPosition  = intent.getIntExtra("Class Position", -1)
        studentPosition  = intent.getIntExtra("Student Position", -1)
        position = intent.getIntExtra("Score Position",-1)
    }

    private fun listener() {
        editScoreBackFAB.setOnClickListener {
            finish()
        }

        deleteScoreBtn.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = user.uid
                val database = Firebase.database
                val myRef = database.getReference("users")
                val cid = GlobalVar.classArrayList[classPosition].id
                val sid = GlobalVar.classArrayList[classPosition].students[studentPosition].id
                val scoreid = GlobalVar.classArrayList[classPosition].students[studentPosition].scores[position].id
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("scores").child(scoreid).removeValue()
                finish()
            }
        }

        updateScoreBtn.setOnClickListener {
            val name = editScoreNameTIL.editText?.text.toString().trim();
            val note = editScoreNoteTIL.editText?.text.toString().trim();
            val score = editScoreScoreTIL.editText?.text.toString().trim();

            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = user.uid
                //Tunggu mimi confirm
                val cid = GlobalVar.classArrayList[classPosition].id
                val sid = GlobalVar.classArrayList[classPosition].students[studentPosition].id
                val scoreid = GlobalVar.classArrayList[classPosition].students[studentPosition].scores[position].id
//                Toast.makeText(this,cid, Toast.LENGTH_SHORT).show()
                val myRef = FirebaseDatabase.getInstance().getReference("users")
                val student =  mapOf<String,String>(
                    "id" to scoreid,
                    "note" to note,
                    "name" to name,
                    "value" to score,
                )
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("scores").child(scoreid).child("value").setValue(score)
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("scores").child(scoreid).child("note").setValue(note)
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("scores").child(scoreid).child("name").setValue(name).addOnSuccessListener {
                    Toast.makeText(this,"Data Updated", Toast.LENGTH_SHORT).show()
                }

                finish()

            }
        }
    }
}