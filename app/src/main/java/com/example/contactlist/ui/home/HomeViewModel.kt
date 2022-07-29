package com.example.contactlist.ui.home

import androidx.lifecycle.*
import com.example.contactlist.data.model.BaseItem
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.model.Title
import com.example.contactlist.data.repository.ContactRepository

class HomeViewModel(private val contactRepository: ContactRepository): ViewModel() {
    private val _contacts: MutableLiveData<List<BaseItem>> = MutableLiveData()
    val contacts: LiveData<List<BaseItem>> = _contacts

    val emptyScreen: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        getContacts()
    }


    private fun getContacts() {
        val response = contactRepository.getContacts()
        val tempList: MutableList<BaseItem> = mutableListOf()
        val len = response.size
        for(i in 1..len){
            if(i % 2 == 0){
                val title = Title("Hello ${response[i-1].name}")
                tempList.add(title)
            }
            tempList.add(response[i - 1])
        }
        _contacts.value = tempList
        emptyScreen.value = _contacts.value.isNullOrEmpty()
    }

    fun onDeleteClicked(id: Int){
        contactRepository.deleteContact(id)
        refresh()
    }

    fun refresh() {
        getContacts()
    }

    class Provider(private val repository: ContactRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(repository) as T
            }

            throw IllegalArgumentException("Invalid ViewModel")
        }
    }

}