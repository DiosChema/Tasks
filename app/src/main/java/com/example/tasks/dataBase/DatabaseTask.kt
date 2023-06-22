package com.example.tasks.dataBase

import Task
import android.content.ContentValues
import com.example.tasks.data.Generics

class DatabaseTask(private val databaseHelper: DatabaseHelper) {

    val genericos = Generics()

    fun newTask(title: String, description: String, priority: Int): Task {
        // Guardar la tarea en la tabla "Task"
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("title", title)
            put("description", description)
            put("priority", priority)
            put("isCompleted", 0)
            put("created_at", genericos.getCurrentDateTime())
        }
        val insertedId = db.insert("Task", null, contentValues)

        //Regresar Task
        return Task(insertedId, title, description, priority, false, genericos.getCurrentDateTime())
    }

    fun removeTask(task: Task) {
        // Guardar task ebn tabla "TaskHistory"
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("title", task.title)
            put("description", task.description)
            put("priority", task.priority)
            put("isCompleted", task.isCompleted)
            put("created_at", task.createdAt)
            put("deleted_at", genericos.getCurrentDateTime())
        }
        db.insert("TaskHistory", null, contentValues)

        // Eliminar la tarea de la tabla "Task"
        db.delete("Task", "id = ?", arrayOf(task.id.toString()))
    }

    fun getAllTasks(): ArrayList<Task> {
        val taskList = ArrayList<Task>()
        val db = databaseHelper.readableDatabase

        val columns = arrayOf("id", "title", "description", "priority", "created_at", "isCompleted")

        val cursor = db.query("Task", columns, null, null, null, null, "priority DESC, created_at ASC")

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
            val priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"))
            val createdAt = cursor.getString(cursor.getColumnIndexOrThrow("created_at"))
            val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("isCompleted")) == 1

            val task = Task(id, title, description, priority, isCompleted, createdAt)
            taskList.add(task)
        }

        cursor.close()

        return taskList
    }

    fun updateTaskCompleted(task: Task) {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("isCompleted", task.isCompleted)
        }
        db.update("Task", contentValues, "id = ?", arrayOf(task.id.toString()))
    }

}