package com.example.usertaskapp.data.localdatabase

import androidx.lifecycle.LiveData
import com.example.usertaskapp.data.localdatabase.model.Todo
import com.example.usertaskapp.data.localdatabase.model.User
import com.example.usertaskapp.data.localdatabase.model.UserandTodos

class Repository(private val userDao: UserDao){

    suspend fun insert(user: User) {
        return userDao.insert(user)
    }

    suspend fun getUserName(userName: String):User?{
        return userDao.getUsername(userName)
    }

fun getAll(id:Int): LiveData<List<Todo>> {
    return userDao.getAll(id)
}
    suspend fun delettodo(todo: Todo){
        userDao.deleteTodo(todo)
    }

    fun getDone():LiveData<List<Todo>>{
        return userDao.getDone()
    }

    suspend fun gettodo(id:Long):Todo{
        return  userDao.getTodo(id)

    }
    fun getUserData(userId:Int):LiveData<UserandTodos>{
        return userDao.getUserAndTodos(userId)
    }
   suspend fun updateExistTodo(todo: Todo) {
            userDao.updateTodo(todo)
    }
    suspend fun insertNewTodo(todo: Todo): Long {
        return userDao.insertTodo(todo)
    }


  suspend  fun addTodoTodata(todo: Todo) {
            var Id = todo.id
            if (todo.id > 0) { updateExistTodo(todo) } else { Id = insertNewTodo(todo) }
    }
}