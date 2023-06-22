package com.example.tasks.dataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// 1. Configuración de SQLiteOpenHelper
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "task_db"
        private const val DATABASE_VERSION = 1

        // Definición de la estructura de la tabla "Task"
        private const val CREATE_TASK_TABLE = "CREATE TABLE Task (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "description TEXT," +
                "priority INTEGER," +
                "isCompleted Boolean," +
                "created_at DATETIME" +
                ")"

        // Definición de la estructura de la tabla "TaskHistory"
        private const val CREATE_TASK_HISTORY_TABLE = "CREATE TABLE TaskHistory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "description TEXT," +
                "priority INTEGER," +
                "isCompleted Boolean," +
                "created_at DATETIME," +
                "deleted_at DATETIME" +
                ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear las tablas
        db.execSQL(CREATE_TASK_TABLE)
        db.execSQL(CREATE_TASK_HISTORY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Para actualizar las tablas si es necesario
    }

}
