package com.example.iscore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main_menu.*


class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val ref = FirebaseDatabase.getInstance().getReference(user).child(user.uid)
            ref.child(user.getUid()).setValue(user_class);
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            hellomainmenu_textView.setText("Hello, " + name)
        }


        Listener()
    }

    private fun Listener(){
        mainMenuFAB.setOnClickListener {
            val myIntent = Intent(this, LoginActivity::class.java)
            startActivity(myIntent)
        }
        updateprofile_button.setOnClickListener {
            val myIntent = Intent(this, UpdateProfileActivity::class.java)

            startActivity(myIntent)
        }

        viewclasslist_button.setOnClickListener {
            val myIntent = Intent(this, ClassListActivity::class.java)

            startActivity(myIntent)
        }

        studentlist_button.setOnClickListener {
            val myIntent = Intent(this, StudentListActivity::class.java)

            startActivity(myIntent)
        }
    }
}