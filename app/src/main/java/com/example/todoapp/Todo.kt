package com.example.todoapp

data class Todo (
    val _id:Int = 0,
    val title:String,
    var isChecked:Int = 0
)