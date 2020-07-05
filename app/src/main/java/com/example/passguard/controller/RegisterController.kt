package com.example.passguard.controller

import android.content.Context
import com.example.passguard.dao.DatabaseController
import java.lang.Exception

class RegisterController(private val context: Context)
{
    fun insert(passwordDescription : String, passwordContent: String, id_user : Int) : Boolean
    {
        return try {
            DatabaseController(context).insert(passwordDescription, passwordContent, id_user)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }
}