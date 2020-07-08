package com.example.passguard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.passguard.notification.NotificationController
import com.example.passguard.util.IS_PASSWORD
import com.example.passguard.session.Session
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        val field = findViewById<EditText>(R.id.authenticationEdit)
        val isPassword = intent.getBooleanExtra(IS_PASSWORD, true)

        if (isPassword)
            field.setHint(R.string.password)
        else
            field.setHint(R.string.code)

        authenticationBtn.setOnClickListener {
            val authString = field.text
            var authUser = false

            if (isPassword)
            {
                if (Session.getInstance().getUser()?.password == authString.toString())
                    authUser = true
                else
                    errorTxt.setText(R.string.invalidPassword)
            }
            else
            {
                if (NotificationController.getCode() == authString.toString())
                    authUser = true
                else
                    errorTxt.setText(R.string.invalidEmail)
            }

            if (authUser)
            {
                Toast.makeText(this, R.string.userAuthenticated, Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }
            else
                Session.destroy()
        }
    }

    override fun onResume() {
        super.onResume()
        errorTxt.text = null
        authenticationEdit.text = null
    }
}