package com.danilosp.todolist.view

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.danilosp.todolist.R
import com.danilosp.todolist.databinding.ItemTodoBinding
import com.danilosp.todolist.model.Todo
import kotlinx.android.synthetic.main.item_todo.view.*
import java.util.*


class ToDoListAdapter(todoList: ArrayList<Todo>) : RecyclerView.Adapter<ToDoListAdapter.TodoViewHolder>() {

    private val todoList: ArrayList<Todo> = todoList

    var onItemClick : ((Todo) -> Unit)? = null
    var onItemLongClick : ((Todo) -> Unit)? = null
    var onItemCheckBoxClick : ((Todo) -> Unit)? = null

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemTodoBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_todo, parent, false)
        return TodoViewHolder(view, onItemClick, onItemLongClick, onItemCheckBoxClick)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int ) {
        holder.itemview.todo = todoList[position]
        holder.bindView(todoList[position], position)


    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    class TodoViewHolder(
        itemView: ItemTodoBinding,
        private val onItemClick: ((Todo) -> Unit)?,
        private val onItemLongClick: ((Todo) -> Unit)?,
        private val onItemCheckBoxClick: ((Todo) -> Unit)?
    ) :
        RecyclerView.ViewHolder(itemView.root) {
        var itemview: ItemTodoBinding = itemView

        fun bindView(item : Todo, position : Int){
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }

            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(item)
                return@setOnLongClickListener true
            }

            itemView.todoDone.setOnClickListener {
                onItemCheckBoxClick?.invoke(item)
            }
        }

    }
}

