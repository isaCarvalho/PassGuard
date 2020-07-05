package com.example.passguard.controller

import android.content.Context
import com.example.passguard.dao.DatabaseController
import com.example.passguard.session.Session

class LoginController(context: Context)
{
    private var databaseController : DatabaseController  = DatabaseController(context)

    fun authenticate(email : String, password : String)
    {
        val user = databaseController.getUser(email, password)

        if (user != null)
            Session.saveInstance(user)
    }
}