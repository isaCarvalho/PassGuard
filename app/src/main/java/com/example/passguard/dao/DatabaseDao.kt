package com.example.passguard.dao

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.passguard.model.Register
import com.example.passguard.model.User

class DatabaseDao(private val context: Context)
{
    private val dbHelper = DatabaseCreator(context)

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
}