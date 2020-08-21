package com.danilosp.todolist.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.danilosp.todolist.model.Todo
import com.danilosp.todolist.model.TodoDao
import com.danilosp.todolist.model.TodoDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    var todos: MutableLiveData<List<Todo>> = MutableLiveData()
    var todoLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    var context = getApplication<Application>().applicationContext

    private var db: TodoDatabase? = null
    private var todoDao: TodoDao? = null

    fun refresh() {

        Observable.fromCallable {
            db = context?.let { TodoDatabase.getAppDataBase(context = context) }
            todoDao = db?.todoDao()
            db?.todoDao()?.getAll()

        }.doOnNext { list ->
            val todoList: ArrayList<Todo> = ArrayList()

            list?.map {
                Log.d("RESULT", it.toString())
                todoList.add(it)
            }

            todos.postValue(todoList)
            todoLoadError.postValue(true)
            loading.postValue(false)

            todosRetrieved(todoList)

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun todosRetrieved(todosList: List<Todo>) {
        if(todosList.isNotEmpty()){
            todos.postValue(todosList)
            todoLoadError.postValue(false)

        }else{
            todoLoadError.postValue(true)
        }
        loading.postValue(false)
    }

    fun insertTodo(todo : Todo) {
        Observable.fromCallable {
            db = context?.let { TodoDatabase.getAppDataBase(context = context) }

            todoDao = db?.todoDao()

            with(todoDao) {
                this?.insertOne(todo)
            }
            db?.todoDao()?.getAll()

        }.doOnNext { list ->
            var finalString = ""
            list?.map {
                Log.d("RESULT", it.toString())
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun deleteTodo(todoId: Int) {
        Observable.fromCallable {
            db = context?.let { TodoDatabase.getAppDataBase(context = context) }

            todoDao = db?.todoDao()

            with(todoDao) {
                this?.deleteOne(todoId)
            }
            db?.todoDao()?.getAll()

        }.doOnNext { list ->
            var finalString = ""
            list?.map {
                Log.d("RESULT", it.toString())
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun updateTodo(todo: Todo) {
        Observable.fromCallable {
            db = context?.let { TodoDatabase.getAppDataBase(context = context) }

            todoDao = db?.todoDao()

            with(todoDao) {
                this?.updateOne(todo.uuid, todo.done)
            }
            db?.todoDao()?.getAll()

        }.doOnNext { list ->
            var finalString = ""
            list?.map {
                Log.d("RESULT", it.toString())
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun deleteAll() {

        Observable.fromCallable {
            db = context?.let { TodoDatabase.getAppDataBase(context = context) }
            todoDao = db?.todoDao()
            db?.todoDao()?.deleteAll()

        }.doOnNext { list ->

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}
