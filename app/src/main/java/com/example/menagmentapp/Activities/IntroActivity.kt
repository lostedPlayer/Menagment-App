package com.example.menagmentapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.menagmentapp.R
import com.google.firebase.FirebaseApp


class IntroActivity : BaseActivity() {

    lateinit var mSingUpButton: Button
    lateinit var mSingInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        FirebaseApp.initializeApp(this)

        //initialise views
        mSingUpButton = findViewById(R.id.button_sing_up)
        mSingInButton = findViewById(R.id.button_sing_in)

        //go to register screen
        mSingUpButton.setOnClickListener {
            val startsingUpActivity = Intent(this, singUpActivity::class.java)
            startActivity(startsingUpActivity)
        }


        //go to sing in screen
        mSingInButton.setOnClickListener {
            val startSingInActivity = Intent(this, singInActivity::class.java)
            startActivity(startSingInActivity)
        }


    }
}