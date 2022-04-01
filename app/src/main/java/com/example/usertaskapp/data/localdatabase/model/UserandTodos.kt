package com.example.usertaskapp.data.localdatabase.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserandTodos(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn ="userOwnerId"
    )
    val todo: MutableList<Todo>
)
