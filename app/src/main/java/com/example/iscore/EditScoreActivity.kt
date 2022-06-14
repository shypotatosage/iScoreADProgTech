package com.example.iscore

import Database.GlobalVar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_score.*

class EditScoreActivity : AppCompatActivity() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_score)
        GetIntent()
        listener()
    }
    private fun GetIntent() {
        position  = intent.getIntExtra("Position", -1)
    }

    private fun listener() {
        editScoreBackFAB.setOnClickListener {
            finish()
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
                val cid = GlobalVar.classArrayList[position].id
                val sid = GlobalVar.classArrayList[position].id
//                Toast.makeText(this,cid, Toast.LENGTH_SHORT).show()
                val myRef = FirebaseDatabase.getInstance().getReference("users")
                val student =  mapOf<String,String>(
                    "id" to sid,
                    "note" to note,
                    "name" to name,
                    "score" to score,
                )
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("score").setValue(score)
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("note").setValue(note)
                myRef.child("users").child(uid).child("classes").child(cid).child("students").child(sid).child("name").setValue(name).addOnSuccessListener {
                    Toast.makeText(this,"Data Updated", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}