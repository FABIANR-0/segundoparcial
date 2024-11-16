package com.example.myapplication.helpers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.segundoparcial.model.User

class DataBaseHelper(context : Context)  : SQLiteOpenHelper(context, "user.db", null, 1) {


    companion object {
        private const val TABLE_NAME = "USER"
        private const val COLUMN_ID = "ID"
        private const val COLUMN_NAME = "NAME"
        private const val COLUMN_TYPE = "TYPE"
        private const val COLUMN_DESCRIPTION = "DESCRIPTION"
        private const val COLUMN_IMG = "IMG"
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_TYPE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_IMG TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertName(user: User) : Long{
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_DESCRIPTION, user.description)
            put(COLUMN_TYPE, user.type)
            put(COLUMN_IMG, user.img)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            val user = User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                img = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG))
            )
            userList.add(user)
        }
        cursor.close()
        return userList
    }

    fun updateUser(user: User): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_DESCRIPTION, user.description)
            put(COLUMN_TYPE, user.type)
            put(COLUMN_IMG, user.img)
        }
        return db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(user.id.toString()))
    }

    fun deleteUser(userId: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(userId.toString()))
    }
}


















