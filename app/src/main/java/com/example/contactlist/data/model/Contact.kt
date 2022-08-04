package com.example.contactlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String,
    val phone: String
): BaseItem(){
    override fun getType(): String {
        return ITEM_TYPE
    }
}

