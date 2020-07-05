package com.example.passguard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.passguard.controller.UserController
import com.example.passguard.util.Validate
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var createAccountBtn : Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createAccountBtn = findViewById(R.id.createBtn)
        createAccountBtn.setOnClickListener {
            val name = createUsernameEdit.text.toString()

            val errorTxt = findViewById<TextView>(R.id.errorCreateAccount)

            val email = findViewById<EditText>(R.id.createEmailEdit).text.toString()
            var message = Validate.isEmailValid(email)

            if (!message.isNullOrEmpty())
            {
                errorTxt.text = message
                return@setOnClickListener
            }

            val confirmEmail = findViewById<EditText>(R.id.confirmEmailEdit).text.toString()
            if (email != confirmEmail)
            {
                errorTxt.setText(R.string.confirmEmailError)
                return@setOnClickListener
            }

            val password = findViewById<EditText>(R.id.createPasswordEdit).text.toString()
            message = Validate.isPasswordValid(password)

            if (!message.isNullOrEmpty())
            {
                errorTxt.text = message
                return@setOnClickListener
            }

            val confirmPassword = findViewById<EditText>(R.id.confirmPasswordEdit).text.toString()
            if (password != confirmPassword)
            {
                errorTxt.setText(R.string.confirmPasswordError)
                return@setOnClickListener
            }

            if (UserController(this).insert(name, email, password)) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                errorTxt.text = "Não foi possível criar a conta"
            }
        }
    }
}