package com.example.attendance.presentation.util

fun String.firstName(): String{
    return this.trim().substringBefore(' ')
}

fun String.initials(): String{
    return this.trim().split(' ').filter { it.isNotBlank() }.take(2).joinToString(""){it.first().uppercase()}
}