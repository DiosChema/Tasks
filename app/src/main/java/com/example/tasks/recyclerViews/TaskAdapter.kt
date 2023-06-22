package com.example.tasks.recyclerViews

import Task
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.data.Generics

class TaskAdapter(
    private val taskList: ArrayList<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskCheckedChangeListener: OnTaskCheckedChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        val generics = Generics()
        val container = holder.itemView.findViewById<FrameLayout>(R.id.container)

        holder.textViewTaskTitle.text = currentTask.title
        holder.textViewTaskDescription.text = currentTask.description

        // Set priority color
        val priorityColor = generics.getPriorityColor(currentTask.priority)

        val backgroundDrawable = container.background as GradientDrawable
        backgroundDrawable.setColor(priorityColor)

        holder.checkBoxTaskCompleted.isChecked = currentTask.isCompleted

        // Formatear y establecer la fecha
        val formattedDate = generics.formatDateTime(currentTask.createdAt)
        holder.textViewTaskDate.text = formattedDate

        holder.checkBoxTaskCompleted.setOnCheckedChangeListener { _, isChecked ->
            currentTask.isCompleted = isChecked
            taskCheckedChangeListener?.onTaskCheckedChange(currentTask, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun removeTask(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            taskList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun setOnTaskCheckedChangeListener(listener: OnTaskCheckedChangeListener) {
        taskCheckedChangeListener = listener
    }

    fun getTaskAtPosition(position: Int): Task {
        return taskList[position]
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTaskTitle: TextView = itemView.findViewById(R.id.textViewTaskTitle)
        val textViewTaskDescription: TextView = itemView.findViewById(R.id.textViewTaskDescription)
        val textViewTaskDate: TextView = itemView.findViewById(R.id.textViewTaskDate)
        val checkBoxTaskCompleted: CheckBox = itemView.findViewById(R.id.checkBoxTaskCompleted)
    }

    interface OnTaskCheckedChangeListener {
        fun onTaskCheckedChange(task: Task, isChecked: Boolean)
    }
}
