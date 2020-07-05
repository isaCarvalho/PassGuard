package com.example.passguard.controller

import android.content.Context
import com.example.passguard.dao.DatabaseController
import com.example.passguard.model.Register
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

    fun list(idUser : Int) : ArrayList<Register>
    {
        val registers = ArrayList<Register>()

        DatabaseController(context).listRegisters(idUser).forEach { item ->
            registers.add(item)
        }

        return registers
    }

    fun delete(id : Int) : Boolean
    {
        return try {
            DatabaseController(context).delete(id, false)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }
}