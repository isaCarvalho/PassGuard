package com.example.passguard.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseCreator(context : Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
)
{
    companion object
    {
        const val DATABASE_NAME = "passguard.db"
        const val DATABASE_VERSION = 1
    }

    object FeedReaderContract
    {
        object FeedEntry : BaseColumns
        {
            // table users
            const val TABLE_USERS = "users"
            const val USER_NAME = "name"
            const val USER_EMAIL = "email"
            const val USER_PASSWORD = "password"

            // table passwords
            const val TABLE_REGISTERS = "registers"
            const val PASSWORD_DESCRIPTION = "passwordDescription"
            const val PASSWORD_CONTENT = "passwordContent"
            const val ID_USER = "id_user"
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var sql = "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_USERS} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL, " +
                "${FeedReaderContract.FeedEntry.USER_NAME} TEXT NOT NULL, " +
                "${FeedReaderContract.FeedEntry.USER_EMAIL} TEXT NOT NULL, " +
                "${FeedReaderContract.FeedEntry.USER_PASSWORD} TEXT NOT NULL )"

        db?.execSQL(sql)

        sql = "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_REGISTERS} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL, " +
                "${FeedReaderContract.FeedEntry.PASSWORD_DESCRIPTION} TEXT NOT NULL, " +
                "${FeedReaderContract.FeedEntry.PASSWORD_CONTENT} TEXT NOT NULL, " +
                "${FeedReaderContract.FeedEntry.ID_USER} INTEGER NOT NULL, " +
                "FOREIGN KEY (${FeedReaderContract.FeedEntry.ID_USER}) REFERENCES ${FeedReaderContract.FeedEntry.TABLE_USERS}(${BaseColumns._ID}) )"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_USERS};" +
                "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_REGISTERS};"

        db?.execSQL(sql)
        onCreate(db)
    }
}