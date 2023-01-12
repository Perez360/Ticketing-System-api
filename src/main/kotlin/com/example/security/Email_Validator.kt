package com.example.security

fun validateEmail(email:String):Boolean{
    val emailPattern="[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"
    return  (email.matches(email.toRegex()))
}