package com.example.passguard.controller

import android.content.Context
import com.example.passguard.dao.DatabaseController
import com.example.passguard.model.User
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

    fun delete(id : Int) : Boolean
    {
        return try {
            DatabaseController(context).delete(id, true)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }

    fun edit(user: User) : Boolean
    {
        return try {
            DatabaseController(context).update(user)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }
}