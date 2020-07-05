package com.example.passguard.dao

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.passguard.model.Register
import com.example.passguard.model.User
import java.lang.Exception

class DatabaseController(private val context: Context)
{
    private val dbHelper = DatabaseCreator(context)

    fun insert(name: String, email : String, password : String)
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(DatabaseCreator.FeedReaderContract.FeedEntry.USER_NAME, name)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL, email)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.USER_PASSWORD, password)
        }

        db?.insert(
            DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_USERS,
            null,
            contentValues
        )
    }

    fun insert(passwordDescription: String, passwordContent : String)
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_DESCRIPTION, passwordDescription)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_CONTENT, passwordContent)
        }

        db?.insert(
            DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_REGISTERS,
            null,
            contentValues
        )
    }

    fun delete(id: Int, isUser : Boolean = true)
    {
        val db = dbHelper.writableDatabase

        val table = if (isUser) DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_USERS
            else DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_REGISTERS

        db?.execSQL("DELETE FROM $table " +
                "WHERE ${BaseColumns._ID} = ?",
            arrayOf(id)
        )
    }

    fun update(user : User)
    {
        val db = dbHelper.writableDatabase

        db?.execSQL("UPDATE ${DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_USERS} " +
                "SET ${DatabaseCreator.FeedReaderContract.FeedEntry.USER_NAME} = '?', " +
                "${DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL} = '?', " +
                "${DatabaseCreator.FeedReaderContract.FeedEntry.USER_PASSWORD} = '?' " +
                "WHERE ${BaseColumns._ID} = ?",
            arrayOf(user.name, user.email, user.password, user.id.toString())
        )
    }

    fun update(register: Register)
    {
        val db = dbHelper.writableDatabase

        db?.execSQL("UPDATE ${DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_REGISTERS} " +
                "SET ${DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_DESCRIPTION} = '?', " +
                "${DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_CONTENT} = '?', " +
                "${DatabaseCreator.FeedReaderContract.FeedEntry.ID_USER} = ? " +
                "WHERE ${BaseColumns._ID} = ?",
            arrayOf(register.passwordDescription, register.passwordContent, register.user_id, register.id.toString())
        )
    }

    fun listUsers() : ArrayList<User>
    {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseCreator.FeedReaderContract.FeedEntry.USER_NAME,
            DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL,
            DatabaseCreator.FeedReaderContract.FeedEntry.USER_PASSWORD
        )

        val cursor = db?.query(
            DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_USERS,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val items = ArrayList<User>()
        with(cursor!!) {
            while (moveToNext())
            {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.USER_NAME))
                val email = getString(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL))
                val password = getString(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.USER_PASSWORD))

                val user = User(id, name, email, password)
                items.add(user)
            }
        }

        cursor.close()
        return items
    }

    fun listRegisters(idUser : Int) : ArrayList<Register>
    {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_DESCRIPTION,
            DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_CONTENT,
            DatabaseCreator.FeedReaderContract.FeedEntry.ID_USER
        )

        val cursor = db?.query(
            DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_REGISTERS,
            projection,
            "${DatabaseCreator.FeedReaderContract.FeedEntry.ID_USER} = ?",
            arrayOf(idUser.toString()),
            null,
            null,
            null
        )

        val items = ArrayList<Register>()
        with(cursor!!) {
            while (moveToNext())
            {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val passwordDescription = getString(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_DESCRIPTION))
                val passwordContent = getString(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_CONTENT))
                val id_user = getInt(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.ID_USER))

                val register = Register(id, passwordDescription, passwordContent, id_user)
                items.add(register)
            }
        }

        cursor.close()
        return items
    }

    fun getUser(email: String, password: String) : User? {

        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseCreator.FeedReaderContract.FeedEntry.USER_NAME,
            DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL,
            DatabaseCreator.FeedReaderContract.FeedEntry.USER_PASSWORD
        )

        val cursor = db?.query(
            DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_USERS,
            projection,
            "${DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL} = ? AND ${DatabaseCreator.FeedReaderContract.FeedEntry.USER_PASSWORD} = ?",
            arrayOf(email, password),
            null,
            null,
            null
        )

        var user : User? = null
        with(cursor!!) {
            if (moveToNext())
            {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.USER_NAME))
                val emailUser = getString(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL))
                val passwordUser = getString(getColumnIndexOrThrow(DatabaseCreator.FeedReaderContract.FeedEntry.USER_PASSWORD))

                user = User(id, name, emailUser, passwordUser)
            }
        }

        cursor.close()
        return user
    }
}