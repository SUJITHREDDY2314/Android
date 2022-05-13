package com.example.a7minuteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlDatabase(context:Context,factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME,factory, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HistoryDataBase"
        private const val TABLE_NAME = "History"
        private const val SL_NO = "SlNo"
        private const val DATE_TIME = "DateTime"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_COMMAND = ("CREATE TABLE "+ TABLE_NAME+ "(" + SL_NO + " INTEGER PRIMARY KEY AUTOINCREMENT," + DATE_TIME + " TEXT)")
        db?.execSQL(CREATE_TABLE_COMMAND)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME)
        onCreate(db)
    }
    fun addDate(date:String){
        val values = ContentValues()
        values.put(DATE_TIME,date)
        val db = this.writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun addDate(date:String,id:Int){
        val values = ContentValues()
        values.put(DATE_TIME,date)
        values.put(SL_NO,id)
        val db = this.writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun deleteDate(id:Int){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$SL_NO = $id",null)
        db.close()
    }

    fun getDbList():ArrayList<DateId>{
        val list = ArrayList<DateId>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME",null)
        while (cursor.moveToNext()) {
            val idValue= (cursor.getInt(cursor.getColumnIndex(SL_NO)))
            val dateValue = (cursor.getString(cursor.getColumnIndex(DATE_TIME)))
            list.add(DateId(idValue,dateValue))
        }
        cursor.close()
        return list
    }
}