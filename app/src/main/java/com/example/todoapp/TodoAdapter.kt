package com.example.todoapp

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Array

class TodoAdapter ( private val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val _id:TextView=itemView.findViewById(R.id._id)
        val tvTodoTitle:TextView=itemView.findViewById(R.id.tvTodoTitle)
        val cbDone:CheckBox=itemView.findViewById(R.id.cbDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        // Layout Inflater is enable us to get reference to some specific view. layout inflater will take the xml code of
        // a tod and will convert it into a kotlin class that we use in our code
        val todoView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo,
            parent,
            false
        )
        return TodoViewHolder(todoView);
    }

    override fun getItemCount(): Int {
        return todos.size;
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked:Boolean){
        if(isChecked){
            tvTodoTitle.paintFlags =tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags =tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun addTodo(todo:Todo,databaseHandler:DatabaseHandler){
        todos.add(todo);
        databaseHandler.addTodo(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodo(databaseHandler:DatabaseHandler){
        var ids:ArrayList<Int> = ArrayList()

        for (todo in todos) {
            if(todo.isChecked==1){
                databaseHandler.deleteTodo(todo)
                ids.add(todo._id)
            }
        }
        ids.forEach {id->
            todos.removeAll { t->
                t._id==id
            }
        }
        notifyDataSetChanged()
    }

    //    it will bind data from todos list to the views . This is called when new view holder is visible
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder._id.text=curTodo._id.toString()
        holder.tvTodoTitle.text=curTodo.title
        holder.cbDone.isChecked=curTodo.isChecked==1
        holder.apply {
            _id.text= curTodo._id.toString()
            tvTodoTitle.text = curTodo.title
            cbDone.isChecked = curTodo.isChecked==1
            toggleStrikeThrough(tvTodoTitle,cbDone.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoTitle,isChecked)
                curTodo.isChecked = if(curTodo.isChecked==1) 0 else 1;
            }
        }
    }
}