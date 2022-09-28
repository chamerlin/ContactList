package com.example.contactlist.data.repository

import com.example.contactlist.data.model.User

interface UserRepository {
    suspend fun signUp(user: User): User?
    suspend fun getUsers() : List<User>
}