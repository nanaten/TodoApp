package com.nanaten.todoapp.domain

import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.database.TodoEntity
import io.realm.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TodoRepository {
    suspend fun getTodoList(): Flow<MutableList<Todo>>
    suspend fun addTodo(todo: Todo)
}

class TodoRepositoryImpl : TodoRepository {
    override suspend fun getTodoList(): Flow<MutableList<Todo>> {
        return flow {
            val realm = Realm.getDefaultInstance()
            val entity = realm.where(TodoEntity::class.java).findAll()
            val todos = entity?.map { Todo(it) }?.toMutableList()
            emit(todos ?: mutableListOf())
            realm.close()
        }
    }

    override suspend fun addTodo(todo: Todo) {
        val entity = TodoEntity(todo)
        val realm = Realm.getDefaultInstance()
        realm.use {
            it.executeTransaction { realm ->
                realm.insertOrUpdate(entity)
            }
        }
    }
}