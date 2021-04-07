 package com.example.todoapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ActivityResultBinding

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list=getTodoList()
        todoAdapter= TodoAdapter(list as MutableList<Todo>)

        val rvTodoItems=findViewById<RecyclerView>(R.id.rvTodoItems)
        rvTodoItems.adapter=todoAdapter;
        rvTodoItems.layoutManager=LinearLayoutManager(this)

        val btnAddTodo=findViewById<Button>(R.id.btnAddTodo);
        val btnDeleteDoneTodo=findViewById<Button>(R.id.btnDeleteDoneTodo)
        val btnPersonActivity=findViewById<ImageButton>(R.id.btnPersonActivity)

        btnAddTodo.setOnClickListener {
            val todoTitle=findViewById<EditText>(R.id.etTodoTitle).text.toString()
            if(todoTitle.isNotEmpty()){
                val todo=Todo(0,todoTitle)
                val databaseHandler:DatabaseHandler= DatabaseHandler(this)
                todoAdapter.addTodo(todo,databaseHandler);
                findViewById<EditText>(R.id.etTodoTitle).text.clear()
            }
        }

        btnDeleteDoneTodo.setOnClickListener {
            val databaseHandler:DatabaseHandler= DatabaseHandler(this)
            todoAdapter.deleteDoneTodo(databaseHandler)
        }

        btnPersonActivity.setOnClickListener{
            Intent(this,PersonActivity::class.java).also{
                startActivity(it)
            }
        }


    }

    private fun generateDummyList(size:Int):List<Todo>{
        val list = ArrayList<Todo>()
        for(i in 0 until size){
            val item=Todo(0,"todo number $i",0);
            list+=item;
        }
        return list;
    }

    fun getTodoList():ArrayList<Todo>{
        val databaseHandler:DatabaseHandler= DatabaseHandler(this)
        val todoList:ArrayList<Todo> = databaseHandler.viewTodo()
        return todoList
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu , menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val addContactDialog = AlertDialog.Builder(this)
                .setTitle("Add Contact")
                .setMessage("Do you want to add contact ....")
                .setIcon(R.drawable.ic_user)
                .setPositiveButton("Yes"){ _,_->
                    Toast.makeText(this,"new Contact added",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No"){_ , _ ->
                    Toast.makeText(this, "New Contact not added",Toast.LENGTH_SHORT).show()
                }.create()


        val options = arrayOf("First Item","Second Item","Third Item")
        val singleChoiceDialog = AlertDialog.Builder(this)
                .setTitle("Choose one of the Option")
                .setSingleChoiceItems(options,0){dialog: DialogInterface?, i: Int ->
                    Toast.makeText(this,"You click on ${options[i]}",Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Accept"){ _,_->
                    Toast.makeText(this,"You accepted the singleChoiceItem",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Decline"){_ , _ ->
                    Toast.makeText(this, "You declined the singleChoiceItem",Toast.LENGTH_SHORT).show()
                }.create()

        val multipleChoiceDialog = AlertDialog.Builder(this)
                .setTitle("Choose Mulitiple Options")
                .setMultiChoiceItems(options, booleanArrayOf(false,false,false)){_,i,isChecked ->
                    if(isChecked){
                        Toast.makeText(this,"you Checked ${options[i]}",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"you unchecked ${options[i]}",Toast.LENGTH_SHORT).show()
                    }
                }
                .setPositiveButton("Accept"){ _,_->
                    Toast.makeText(this,"You accepted the MultipleChoiceItem",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Decline"){_ , _ ->
                    Toast.makeText(this, "You declined the MultipleChoiceItem",Toast.LENGTH_SHORT).show()
                }.create()


        when(item.itemId){
            R.id.settings -> singleChoiceDialog.show()
            R.id.addContact -> addContactDialog.show()
            R.id.favourite -> Toast.makeText(this,"Clicked on the Favourite",Toast.LENGTH_SHORT).show()
            R.id.feedBack -> multipleChoiceDialog.show()
            R.id.close -> finish()
        }

        return true
    }

}