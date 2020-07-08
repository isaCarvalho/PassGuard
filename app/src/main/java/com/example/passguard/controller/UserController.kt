package com.example.passguard.controller

import android.content.Context
import com.example.passguard.dao.UserDao
import com.example.passguard.model.User
import java.lang.Exception

class UserController(private val context: Context)
{
    private val userDao = UserDao(context)

    fun insert(name: String, email : String, password : String) : Boolean
    {
        return try {
            userDao.insert(name, email, password)

            true
        } catch (e : Exception) {
            print(e.message.toString())

            false
        }
    }

    fun delete(id : Int) : Boolean
    {
        return try {
            userDao.delete(id)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }

    fun edit(user: User) : Boolean
    {
        return try {
            userDao.update(user)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }
}