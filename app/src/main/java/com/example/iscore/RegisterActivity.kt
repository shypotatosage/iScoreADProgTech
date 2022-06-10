package com.example.iscore

import Database.GlobalVar
import Model.User
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        listener()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val user = Firebase.auth.currentUser

            val myIntent = Intent(this, MainMenuActivity::class.java)

            startActivity(myIntent)
            finish()
        }
    }

    private fun listener() {
        registerBtn.setOnClickListener {
            var userValid: Boolean = true
            val uname = usernameTIL.editText?.text.toString().trim();
            val email = emailTIL.editText?.text.toString().trim();
            val password = passwordTIL.editText?.text.toString().trim();

            if (uname.isEmpty()) {
                usernameTIL.error = "Username is required."
                userValid = false
            } else {
                usernameTIL.error = ""
            }

            if (email.isEmpty()) {
                emailTIL.error = "Email is required"
                userValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailTIL.error = "Please enter a valid email"
                userValid = false
            } else {
                emailTIL.error = ""
            }

            if (password.isEmpty()) {
                passwordTIL.error = "Password is required."
                userValid = false
            } else if (password.length < 8) {
                passwordTIL.error = "Password must be 8-20 characters"
                userValid = false
            }  else {
                passwordTIL.error = ""
            }

            if (userValid) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser

                            saveData(
                                user!!.uid, usernameTIL.editText!!.text.toString().trim(),
                                user!!.email!!)

                            val myIntent = Intent(this, LoginActivity::class.java)

                            startActivity(myIntent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun saveData(userID: String, uname: String, email: String) {
        val database = Firebase.database
        val ref = database.getReference("users")

        var usr = User(userID, uname, email)

        ref.child("users").child(userID).setValue(usr).addOnCompleteListener {
            Toast.makeText(applicationContext, "User successfully registered!", Toast.LENGTH_LONG).show()
        }
    }
}