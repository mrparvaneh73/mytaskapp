package com.example.usertaskapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.usertaskapp.databinding.TodoItemBinding
import com.example.usertaskapp.data.localdatabase.model.Todo

class TodoListAdapter(private var onEdit: (Todo) -> Unit, private var onDelete: (Todo) -> Unit) :
    ListAdapter<Todo, TodoListAdapter.TodoListViewHolder>(TodoDiffCallback()) {

    class TodoListViewHolder(
        private val binding: TodoItemBinding,
        private var onEdit: (Todo) -> Unit,
        private var onDelete: (Todo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var taskId: Long = -1
        private var titleView = binding.title
        private var description = binding.description
        private var image = binding.imageview
        private var date =binding.tvDate
        private var todo:Todo ? = null

        @SuppressLint("SetTextI18n")
        fun bind(todo: Todo) {
            taskId = todo.id
            titleView.text = todo.title
            description.text = todo.description
            date.text = "${todo.date} & ${todo.time}"
            image?.let {
                if (titleView.text.isEmpty()){
                    it.text="?"
                }else{
                    it.text=todo.title[0].toString()
                }


            }


            this.todo = todo
            binding.deleteButton.setOnClickListener {
                onDelete(todo)
            }
            binding.root.setOnClickListener {
                onEdit(todo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {

        return TodoListViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onEdit,
            onDelete
        )
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}