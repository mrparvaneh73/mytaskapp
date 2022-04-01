package com.example.usertaskapp.ui.home.dialog

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.usertaskapp.data.localdatabase.Repository
import com.example.usertaskapp.data.localdatabase.model.Todo
import kotlinx.coroutines.launch

class CustomDialogViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application), Observable {
    private var taskLiveData: LiveData<Todo>? = null

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            repository.addTodoTodata(todo)
        }
    }
    fun getAll(id:Int): LiveData<List<Todo>> {
        return repository.getAll(id)
    }
    fun delete(todo: Todo) = viewModelScope.launch {
        repository.delettodo(todo)
    }

    fun gettodo(id: Long): LiveData<Todo> {
        return taskLiveData ?: liveData {
            emit(repository.gettodo(id))
        }.also {
            taskLiveData = it
        }
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}