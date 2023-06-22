package com.example.tasks.activities

import AddTaskDialog
import Task
import com.example.tasks.recyclerViews.TaskAdapter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.dataBase.DatabaseHelper
import com.example.tasks.dataBase.DatabaseTask
import com.example.tasks.recyclerViews.TaskItemTouchHelperCallback

class MainActivity : AppCompatActivity(), AddTaskDialog.AddTaskDialogListener, TaskAdapter.OnTaskCheckedChangeListener{
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList: ArrayList<Task> = ArrayList()
    private lateinit var databaseTask:DatabaseTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseTask = DatabaseTask(DatabaseHelper(this))

        // Inicializar RecyclerView y TaskAdapter
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)
        taskList = databaseTask.getAllTasks()

        taskAdapter = TaskAdapter(taskList)

        val taskAdapter = TaskAdapter(taskList)
        taskAdapter.setOnTaskCheckedChangeListener(this)

        recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        taskAdapter.notifyDataSetChanged()

        // Configurar el ItemTouchHelper después de la configuración del adaptador y el LayoutManager
        val itemTouchHelperCallback = TaskItemTouchHelperCallback(taskAdapter, this)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewTasks)

        // Escucha de botón Agregar
        val buttonAddTask: Button = findViewById(R.id.buttonAddTask)
        buttonAddTask.setOnClickListener {
            showAddTaskDialog()
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun showAddTaskDialog() {
        val dialog = AddTaskDialog()

        dialog.show(supportFragmentManager, "addTaskDialog")
    }

    override fun onTaskAdded(title: String, description: String, priority: Int) {
        // Se crea metodo para agregar Task
        taskList.add(databaseTask.newTask(title, description, priority))

        //val task = Task(title, description, priority)
        taskAdapter.notifyDataSetChanged()
    }

    override fun onTaskCheckedChange(task: Task, isChecked: Boolean) {
        task.isCompleted = isChecked
        // Aquí realizas la actualización en la base de datos
        databaseTask.updateTaskCompleted(task)
    }

}
