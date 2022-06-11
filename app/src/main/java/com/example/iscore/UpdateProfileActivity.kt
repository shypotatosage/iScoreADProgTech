package com.example.iscore

import Database.GlobalVar
import Model.User
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_update_profile.*


class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        auth = Firebase.auth
        listener()

       updateProfileBackFAB.setOnClickListener {
            val intent = Intent(this,MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            username = getUsername(currentUser.uid).toString()
//            GlobalVar.user = User(currentUser.uid, username, currentUser.email!!)
//        }
//    }

    private fun listener() {
        updateProfileBtn.setOnClickListener {
            var userValid: Boolean = true
            val uname = unameUpdateTIL.editText?.text.toString().trim();
            val email = emailUpdateTIL.editText?.text.toString().trim();
            val password = passwordUpdateTIL.editText?.text.toString().trim();

//            if (uname.isEmpty()) {
//                usernameTIL.error = "Username is required."
//                userValid = false
//            } else {
//                usernameTIL.error = ""
//            }
////
//            if (email.isEmpty()) {
//                emailTIL.error = "Email is required"
//                userValid = false
//            } else {
//                emailTIL.error = ""
//            }
//
//            if (userValid == true) {

//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
//                        val user = auth.currentUser
//
//                        saveData(
//                            user!!.uid,unameUpdateTIL.editText!!.text.toString().trim(),
//                            user!!.email!!)
//
//                        val myIntent = Intent(this, LoginActivity::class.java)
//
//                        startActivity(myIntent)
//                        finish()
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                    }
//                }
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                // Name, email address, and profile photo Url
                val uid = user.uid
//                val database = Firebase.database
//                val myRef = database.getReference("users")
//                myRef.child("users").child(uid).removeValue()
//                user.delete()
//            }

//                    val credential = EmailAuthProvider
//                        .getCredential(email, password)
//
                    //pass tidak bisa terupdate jika email terganti, jika email tetap pass terupdate
                    user.updatePassword(password)
                    user.updateEmail(email)
                    val database = Firebase.database
                    val myRef = FirebaseDatabase.getInstance().getReference("users")
                    val user =  mapOf<String,String>(
                        "uid" to uid,
                        "username" to uname,
                        "email" to email,
                    )
                    myRef.child("users").child(uid).child("email").setValue(email)
                    myRef.child("users").child(uid).child("username").setValue(uname).addOnSuccessListener {
                        Toast.makeText(this,"Data Updated",Toast.LENGTH_SHORT).show()
                    }

            }

//        }
    }
    }
//    private fun saveData(userID: String, uname: String, email: String) {
//        val database = Firebase.database
//        val ref = database.getReference("users")
//
//        var usr = User(userID, uname, email)
//
//        ref.child("users").child(userID).setValue(usr).addOnCompleteListener {
//            Toast.makeText(applicationContext, "Data Updated Please Login Again", Toast.LENGTH_LONG).show()
//        }
//    }

//    private fun getUsername(uid: String) {
//        val database = Firebase.database
//        val ref = database.getReference("users")
//
//        val valueEventListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                username = dataSnapshot.child("users").child(uid).getValue(User::class.java)!!.username;
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.d("Data", databaseError.getMessage()) //Don't ignore errors!
//            }
//        }
//
//        ref.addValueEventListener(valueEventListener)
//    }

}





