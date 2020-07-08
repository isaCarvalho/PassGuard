package com.example.passguard

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.passguard.controller.LoginController
import com.example.passguard.notification.NotificationController
import com.example.passguard.session.Session
import com.example.passguard.util.IS_PASSWORD
import com.example.passguard.util.Validate

class MainActivity : AppCompatActivity() {

    private lateinit var createAccountBtn : Button
    private lateinit var loginPasswordBtn : Button
    private lateinit var loginCodeBtn : Button
    private lateinit var emailTxt : EditText
    private lateinit var error : TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailTxt = findViewById(R.id.emailEdit)
        error = findViewById(R.id.errorTxt)

        if (Session.getInstance().getAuthentication())
        {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
        else
        {
            createAccountBtn = findViewById(R.id.createAccountBtn)
            createAccountBtn.setOnClickListener {
                val intent = Intent(this, CreateAccountActivity::class.java)
                startActivity(intent)
            }

            loginPasswordBtn = findViewById(R.id.loginPasswordBtn)
            loginPasswordBtn.setOnClickListener {
                authenticateEmail()

                if (Session.getInstance().getAuthentication()) {
                    val intent = Intent(this, AuthenticationActivity::class.java).apply {
                        putExtra(IS_PASSWORD, true)
                    }
                    startActivity(intent)
                } else {
                    error.setText(R.string.incorrectEmail)
                }
            }

            loginCodeBtn = findViewById(R.id.loginCodeBtn)
            loginCodeBtn.setOnClickListener {

                authenticateEmail()

                if (Session.getInstance().getAuthentication()) {
                    val notificationController = NotificationController.getInstance(this)
                    notificationController.pushNotification()

                    val intent = Intent(this, AuthenticationActivity::class.java).apply {
                        putExtra(IS_PASSWORD, false)
                    }
                    startActivity(intent)
                } else {
                    error.setText(R.string.incorrectEmail)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        error.text = null
        emailTxt.text = null
    }

    private fun authenticateEmail()
    {
        val email = emailTxt.text.toString()
        val message = Validate.isEmailValid(email)

        if (message != 0) {
            when (message) {
                1 -> error.setText(R.string.emptyEmail)
                2 -> error.setText(R.string.invalidEmail)
            }

            return
        }

        error.text = null

        LoginController(this).authenticateEmail(email)
    }
}