package com.example.passguard.dao

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.passguard.model.User

class UserDao(private val context: Context)
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


    fun delete(id: Int)
    {
        DatabaseDao(context).delete(id, true)
    }

    fun update(user : User)
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(DatabaseCreator.FeedReaderContract.FeedEntry.USER_NAME, user.name)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL, user.email)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.USER_PASSWORD, user.password)
        }

        db?.update(
            DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_USERS,
            contentValues,
            "${BaseColumns._ID} = ?",
            arrayOf(user.id.toString())
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

    fun getUserByEmail(email: String) : User? {

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
            "${DatabaseCreator.FeedReaderContract.FeedEntry.USER_EMAIL} = ?",
            arrayOf(email),
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