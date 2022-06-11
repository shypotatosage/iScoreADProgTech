package com.example.iscore
import Database.GlobalVar
import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        redirectlogin1_textView.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        login_button.setOnClickListener {
            val uname = loginusername_TextInputLayout.editText?.text.toString().trim();
            val password = loginpassword_TextInputLayout.editText?.text.toString().trim();

            if (uname.isEmpty()) {
                usernameTIL.error = "Username is required."
            }

            if (password.isEmpty()) {
                passwordTIL.error = "Password is required."
            }
            auth.signInWithEmailAndPassword(uname, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("A", "signInWithEmail:success")
                        val user = auth.currentUser
                        val username = getUsername(user!!.uid).toString()
                        GlobalVar.user = User(user!!.uid, username, user.email!!)
                        val intent = Intent(this, MainMenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("A", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }

        }
    }

    private fun getUsername(uid: String) {
        val database = Firebase.database
        val ref = database.getReference("users")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                username = dataSnapshot.child("users").child(uid).getValue(User::class.java)!!.username;
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Data", databaseError.getMessage()) //Don't ignore errors!
            }
        }

        ref.addValueEventListener(valueEventListener)
    }
}