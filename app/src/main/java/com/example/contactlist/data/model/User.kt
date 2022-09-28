package com.example.contactlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
