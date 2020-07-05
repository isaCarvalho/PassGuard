package com.example.passguard.controller

import android.content.Context
import com.example.passguard.dao.DatabaseController
import java.lang.Exception

class UserController(private val context: Context)
{
    fun insert(name: String, email : String, password : String) : Boolean
    {
        return try {
            DatabaseController(context).insert(name, email, password)

            true
        } catch (e : Exception) {
            print(e.message.toString())

            false
        }
    }
}