package com.example.tasks.recyclerViews

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.dataBase.DatabaseHelper
import com.example.tasks.dataBase.DatabaseTask

class TaskItemTouchHelperCallback(
    private val taskAdapter: TaskAdapter,
    private val context: Context
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val databaseTask = DatabaseTask(DatabaseHelper(context))
        val position = viewHolder.adapterPosition
        val deletedTask = taskAdapter.getTaskAtPosition(position) // Obtener el objeto Task eliminado
        taskAdapter.removeTask(position)
        databaseTask.removeTask(deletedTask)
    }
}