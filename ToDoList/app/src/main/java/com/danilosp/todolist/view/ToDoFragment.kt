package com.danilosp.todolist.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.danilosp.todolist.R
import com.danilosp.todolist.model.Todo
import com.danilosp.todolist.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.add_todo_dialog.*
import kotlinx.android.synthetic.main.delete_todo_dialog.*
import kotlinx.android.synthetic.main.fragment_to_do.*
import kotlinx.android.synthetic.main.fragment_to_do.view.*
import java.util.*

class ToDoFragment : Fragment() {

    private lateinit var viewModel :TodoViewModel
    private val todoListAdapter: ToDoListAdapter = ToDoListAdapter(ArrayList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_to_do, container,false)
        view.floatingActionButton.setOnClickListener {
            showDialog()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this)[TodoViewModel::class.java]
        viewModel.refresh()

        todoRecyclerView.layoutManager = LinearLayoutManager(context)
        todoRecyclerView.adapter = todoListAdapter

        todoListAdapter.onItemCheckBoxClick = {
            Log.d("Check Click",it.uuid.toString())
            it.done = !it.done
            viewModel.updateTodo(it)
            viewModel.refresh()
        }

        todoListAdapter.onItemClick = {
            Log.d("Click",it.uuid.toString())
            it.done = !it.done
            viewModel.updateTodo(it)
            viewModel.refresh()
        }

        todoListAdapter.onItemLongClick = {
            Log.d("Long Click",it.uuid.toString())
            showDeleteDialog(it.uuid)
        }

        observeViewModel()
    }

    private fun showDialog() {
        val dialog = this.context?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.add_todo_dialog)
            dialog.btnSaveTodo.setOnClickListener {
                if(dialog.edtTitleTodo.text.isEmpty()){
                    dialog.edtTitleTodo.error = "That field must be filled"
                }else{
                    val todo = Todo(null,dialog.edtTitleTodo.text.toString(), dialog.editDescriptionTodo.text.toString(), Calendar.getInstance().time.toString(), false)
                    viewModel.insertTodo(todo)
                    viewModel.refresh()
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }


    private fun showDeleteDialog(uuid: Int?) {
        val dialog = this.context?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.delete_todo_dialog)
            dialog.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.btnDelete.setOnClickListener {

                if (uuid != null) {
                    viewModel.deleteTodo(uuid)
                    viewModel.refresh()
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }

    private fun observeViewModel(){
        viewModel.todos.observe(this, androidx.lifecycle.Observer {
            if(it !=null ){
                todoRecyclerView.visibility = View.VISIBLE
                todoListAdapter.updateTodoList(it)
            }
        })

        viewModel.todoLoadError.observe(this, androidx.lifecycle.Observer {
            if(it != null){
                listEmptyMsg.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, androidx.lifecycle.Observer {
            if(it != null){
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listEmptyMsg.visibility = View.GONE
                    todoRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}