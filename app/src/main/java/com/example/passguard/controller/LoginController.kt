package com.example.passguard.controller

import android.content.Context
import com.example.passguard.dao.UserDao
import com.example.passguard.session.Session

class LoginController(context: Context)
{
    private var userDao : UserDao  = UserDao(context)

    fun authenticateEmail(email : String)
    {
        val user = userDao.getUserByEmail(email)

        if (user != null)
            Session.saveInstance(user)
    }
}