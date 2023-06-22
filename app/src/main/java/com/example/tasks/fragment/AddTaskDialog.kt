import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.tasks.R
import com.example.tasks.data.Generics

class AddTaskDialog : DialogFragment() {

    private lateinit var listener: AddTaskDialogListener
    private var dialogBackgroundColor: Int = 0
    val generics = Generics()

    interface AddTaskDialogListener {
        fun onTaskAdded(title: String, description: String, priority: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as AddTaskDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement AddTaskDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_task, null)

        val editTextTitle = dialogView.findViewById<EditText>(R.id.editTextTaskTitle)
        val editTextDescription = dialogView.findViewById<EditText>(R.id.editTextTaskDescription)
        val radioGroupPriority = dialogView.findViewById<RadioGroup>(R.id.radioGroupTaskPriority)
        val taskDialogContainer = dialogView.findViewById<LinearLayout>(R.id.taskDialogContainer)

        val radioButtonLow = dialogView.findViewById<RadioButton>(R.id.radioButtonLow)
        radioButtonLow.isChecked = true

        val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel)
        val buttonAccept = dialogView.findViewById<Button>(R.id.buttonAccept)

        buttonCancel.setOnClickListener {
            dismiss()
        }

        buttonAccept.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            val priority: Int = when (radioGroupPriority.checkedRadioButtonId) {
                R.id.radioButtonLow -> 1
                R.id.radioButtonMedium -> 2
                R.id.radioButtonHigh -> 3
                else -> 1
            }
            listener.onTaskAdded(title, description, priority)
            dismiss()
        }

        radioGroupPriority.setOnCheckedChangeListener { _, checkedId ->
            dialogBackgroundColor = when (checkedId) {
                R.id.radioButtonLow -> generics.getPriorityColor(1)
                R.id.radioButtonMedium -> generics.getPriorityColor(2)
                R.id.radioButtonHigh -> generics.getPriorityColor(3)
                else -> generics.getPriorityColor(1)
            }

            val backgroundDrawable = taskDialogContainer.background as GradientDrawable
            backgroundDrawable.setColor(dialogBackgroundColor)
        }

        val backgroundDrawable = taskDialogContainer.background as GradientDrawable
        backgroundDrawable.setColor(generics.getPriorityColor(1))

        dialogView.background = ContextCompat.getDrawable(requireActivity(), R.drawable.item_background)


        builder.setView(dialogView)

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
