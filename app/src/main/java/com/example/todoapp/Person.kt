package com.example.todoapp

import java.io.Serializable

data class Person (
    val name:String,
    val email:String,
    val phone:Int
):Serializable