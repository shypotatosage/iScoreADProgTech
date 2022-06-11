package com.example.iscore

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_update_profile.*


class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
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
                val user = FirebaseAuth.getInstance().currentUser

                user?.let {
                val uid = user.uid
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
            }
//        }
    }

}





