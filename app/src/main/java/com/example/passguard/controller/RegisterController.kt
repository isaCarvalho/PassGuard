package com.example.passguard.controller

import android.content.Context
import com.example.passguard.dao.RegisterDao
import com.example.passguard.model.Register
import java.lang.Exception

class RegisterController(private val context: Context)
{
    private val registerDao = RegisterDao(context)

    fun insert(passwordDescription : String, passwordContent: String, id_user : Int) : Boolean
    {
        return try {
            registerDao.insert(passwordDescription, passwordContent, id_user)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }

    fun list(idUser : Int) : ArrayList<Register>
    {
        val registers = ArrayList<Register>()

        registerDao.listRegisters(idUser).forEach { item ->
            registers.add(item)
        }

        return registers
    }

    fun delete(id : Int) : Boolean
    {
        return try {
            registerDao.delete(id)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }

    fun edit(register: Register) : Boolean
    {
        return try {
            registerDao.update(register)
            true
        } catch (e: Exception) {
            print(e.message)
            false
        }
    }
}