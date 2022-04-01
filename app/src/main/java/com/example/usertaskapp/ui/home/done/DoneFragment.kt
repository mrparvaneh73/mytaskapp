package com.example.usertaskapp.ui.home.done

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.usertaskapp.ui.home.MainActivity2
import com.example.usertaskapp.R
import com.example.usertaskapp.TodoListAdapter
import com.example.usertaskapp.databinding.FragmentDoneBinding
import com.example.usertaskapp.data.localdatabase.Repository
import com.example.usertaskapp.data.localdatabase.UserDatabase
import com.example.usertaskapp.data.localdatabase.model.Todo
import com.example.usertaskapp.ui.home.SharedViewModel
import com.example.usertaskapp.ui.home.SharedViewModelFactory

class DoneFragment : Fragment(R.layout.fragment_done) {
    private var date: String = "00"
    private var time: String = "00"
    private lateinit var datepicker: DatePicker
    private lateinit var timepicker: TimePicker
    private lateinit var title: EditText
    private var isdone = false
    private var listdone: MutableList<Todo> = mutableListOf()
    private lateinit var done: CheckBox
    private lateinit var description: EditText
    private lateinit var save: TextView
    private lateinit var cancel: TextView
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentDoneBinding

    @SuppressLint("NewApi")
    private val adapter = TodoListAdapter(
        onEdit = { todoid ->
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.custom_dialog)
            sharedViewModel.gettodo(todoid.id).observe(viewLifecycleOwner) { todoItem ->
                title.setText(todoItem.title)
                description.setText(todoItem.description)
                date = todoItem.date
                time = todoItem.time
                isdone = todoItem.done

            }
            var todo: Todo? = null

            datepicker = dialog.findViewById(R.id.btndatapicker)
            timepicker = dialog.findViewById(R.id.btntimepicker)
            save = dialog.findViewById(R.id.tv_save)
            cancel = dialog.findViewById(R.id.tv_cancel)
            title = dialog.findViewById(R.id.title_et)
            description = dialog.findViewById(R.id.description_et)
            done = dialog.findViewById(R.id.done_checkbox)
            isdone = done.isChecked

            save.setOnClickListener {
                date = "${datepicker.year}/${datepicker.month + 1}/${datepicker.dayOfMonth}"
                Log.d(ContentValues.TAG, "date: " + date)
                time = "${timepicker.hour} : ${timepicker.minute}"
                todo = Todo(
                    todoid.id,
                    title.text.toString(),
                    description.text.toString(),
                    date,
                    time,
                    (activity as MainActivity2?)?.id()!!,
                    isdone
                )
                sharedViewModel.addTodo(todo!!)
                dialog.dismiss()

            }
            cancel.setOnClickListener {
                dialog.dismiss()
            }


            dialog.show()

        },
        onDelete = { todo ->

            sharedViewModel.delete(todo)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDoneBinding.bind(view)
        viewmodelprovider()
        val recyclerView = binding.recyclerView
        Log.d(TAG, "done fragment: "+(activity as MainActivity2?)?.id()!!)
        sharedViewModel.getUserData((activity as MainActivity2?)?.id()!!)
            .observe(viewLifecycleOwner) {

                adapter.submitList( it.todo.filter { todo -> todo.done==true })
                recyclerView.adapter = adapter
            }
    }

    fun viewmodelprovider() {
        val application = requireNotNull(this.activity).application

        val dao = UserDatabase.getInstance(application).userDao

        val repository = Repository(dao)

        val factory = SharedViewModelFactory(repository, application)

        sharedViewModel = ViewModelProvider(this, factory).get(SharedViewModel::class.java)
    }

}

