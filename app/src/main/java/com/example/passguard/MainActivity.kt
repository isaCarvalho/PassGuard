package com.example.passguard

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.passguard.controller.LoginController
import com.example.passguard.session.Session
import com.example.passguard.util.Validate

class MainActivity : AppCompatActivity() {

    private lateinit var createAccountBtn : Button
    private lateinit var loginBtn : Button
    private lateinit var emailTxt : EditText
    private lateinit var passwordTxt : EditText
    private lateinit var error : TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailTxt = findViewById<EditText>(R.id.emailEdit)
        passwordTxt = findViewById<EditText>(R.id.passwordEdit)

        error = findViewById<TextView>(R.id.errorTxt)

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

            loginBtn = findViewById(R.id.loginBtn)
            loginBtn.setOnClickListener {

                val email = emailTxt.text.toString()
                var message = Validate.isEmailValid(email)

                if (!message.isNullOrEmpty()) {
                    error.text = message
                    return@setOnClickListener
                }

                val password = passwordTxt.text.toString()
                message = Validate.isPasswordValid(password)

                if (!message.isNullOrEmpty()) {
                    error.text = message
                    return@setOnClickListener
                }

                error.text = ""

                LoginController(this).authenticate(email, password)

                if (Session.getInstance().getAuthentication()) {
                    Toast.makeText(this, "Usuário autenticado", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ListActivity::class.java)
                    startActivity(intent)
                } else {
                    error.text = "Nome de usuário ou senha incorretos"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        error.text = null
        emailTxt.text = null
        passwordTxt.text = null
    }
}