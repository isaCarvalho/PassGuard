package com.example.passguard

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createAccountBtn = findViewById(R.id.createBtn)
        createAccountBtn.setOnClickListener {
            val name = createUsernameEdit.text.toString()

            val errorTxt = findViewById<TextView>(R.id.errorCreateAccount)

            val email = findViewById<EditText>(R.id.createEmailEdit).text.toString()
            var message = Validate.isEmailValid(email)

            if (message != 0)
            {
                when (message) {
                    1 -> errorTxt.setText(R.string.emptyEmail)
                    2 -> errorTxt.setText(R.string.invalidEmail)
                }
                return@setOnClickListener
            }

            val confirmEmail = findViewById<EditText>(R.id.confirmEmailEdit).text.toString()
            if (email != confirmEmail)
            {
                errorTxt.setText(R.string.notEqualEmail)
                return@setOnClickListener
            }

            val password = findViewById<EditText>(R.id.createPasswordEdit).text.toString()
            message = Validate.isPasswordValid(password)

            if (message != 0)
            {
                when (message) {
                    1 -> errorTxt.setText(R.string.emptyPassword)
                    2 -> errorTxt.setText(R.string.invalidPassword)
                }
                return@setOnClickListener
            }

            val confirmPassword = findViewById<EditText>(R.id.confirmPasswordEdit).text.toString()
            if (password != confirmPassword)
            {
                errorTxt.setText(R.string.notEqualPasswords)
                return@setOnClickListener
            }

            if (UserController(this).insert(name, email, password)) {
                Toast.makeText(this, "Usuario criado com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
            {
                errorTxt.setText(R.string.errorCreatingAccount)
            }
        }
    }
}