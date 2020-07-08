package com.example.passguard.dao

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.passguard.model.Register

class RegisterDao(private val context: Context) {
    private val dbHelper = DatabaseCreator(context)

    fun insert(passwordDescription: String, passwordContent : String, id_user : Int)
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_DESCRIPTION, passwordDescription)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_CONTENT, passwordContent)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.ID_USER, id_user)
        }

        db?.insert(
            DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_REGISTERS,
            null,
            contentValues
        )
    }

    fun delete(id: Int)
    {
        DatabaseDao(context).delete(id, false)
    }

    fun update(register: Register)
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put(DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_DESCRIPTION, register.passwordDescription)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.PASSWORD_CONTENT, register.passwordContent)
            put(DatabaseCreator.FeedReaderContract.FeedEntry.ID_USER, register.user_id)
        }

        db?.update(
            DatabaseCreator.FeedReaderContract.FeedEntry.TABLE_REGISTERS,
            contentValues,
            "${BaseColumns._ID} = ?",
            arrayOf(register.id.toString())
        )
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
}