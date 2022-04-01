package com.example.usertaskapp.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.usertaskapp.data.localdatabase.model.Todo
import com.example.usertaskapp.data.localdatabase.model.User
import com.example.usertaskapp.data.localdatabase.model.UserandTodos

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM Register_users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM Register_users_table WHERE user_name LIKE :userName")
    suspend fun getUsername(userName: String): User?


    @Query("SELECT * FROM todo_table WHERE userOwnerId = :id")
    fun getAll(id:Int): LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getTodo(id: Long): Todo

    @Query("SELECT * FROM todo_table WHERE done = :done")
     fun getDone(done:Boolean=true):LiveData<List<Todo>>

    @Insert
    suspend fun insertTodo(todo: Todo):Long


    @Insert
    suspend fun inserDone(todo:Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Transaction
    @Query("SELECT * FROM register_users_table WHERE userId = :id")
     fun  getUserAndTodos(id: Int):LiveData<UserandTodos>
}