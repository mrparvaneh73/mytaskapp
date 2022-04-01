package com.example.usertaskapp.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.usertaskapp.data.localdatabase.Repository
import com.example.usertaskapp.data.localdatabase.model.Todo
import com.example.usertaskapp.data.localdatabase.model.UserandTodos
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
    private var taskLiveData: LiveData<Todo>? = null
    fun getUserData(userId: Int): LiveData<UserandTodos> {

        return repository.getUserData(userId)
    }

  fun getdone():LiveData<List<Todo>>{
      return repository.getDone()
  }


    fun delete(todo: Todo) = viewModelScope.launch {
        repository.delettodo(todo)
    }
    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            repository.addTodoTodata(todo)
        }
    }
    fun gettodo(id: Long): LiveData<Todo> {
        return taskLiveData ?: liveData {
            emit(repository.gettodo(id))
        }.also {
            taskLiveData = it
        }
    }
}