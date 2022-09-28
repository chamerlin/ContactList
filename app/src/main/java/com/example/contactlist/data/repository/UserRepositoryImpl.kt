package com.example.contactlist.data.repository

import com.example.contactlist.data.model.User

class UserRepositoryImpl: UserRepository {
    private var counter = 1
    var users: MutableMap<Int, User> = mutableMapOf()

    override suspend fun getUsers(): List<User> {
        return users.values.toList()
    }

    override suspend fun signUp(user: User): User? {
        counter++
        users[counter] = user.copy(id = counter)
        return users[counter]
    }

    companion object {
        val instance = UserRepositoryImpl()
    }
}