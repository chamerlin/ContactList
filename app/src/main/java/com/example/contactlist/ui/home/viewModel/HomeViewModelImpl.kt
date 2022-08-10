package com.example.contactlist.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.contactlist.data.model.BaseItem
import com.example.contactlist.data.model.Title
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.data.repository.ContactRepositoryImpl
import com.example.contactlist.ui.base.viewModel.BaseViewModel
import com.example.contactlist.ui.core.viewModel.SafeApiCall
import com.example.contactlist.ui.core.viewModel.SafeApiCallImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(val contactRepository: ContactRepository): HomeViewModel, BaseViewModel(), SafeApiCall by SafeApiCallImpl() {
    private val _contacts: MutableLiveData<List<BaseItem>> = MutableLiveData()
    override val contacts: LiveData<List<BaseItem>> = _contacts

    override val emptyScreen: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _refreshFinished: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val refreshFinished: SharedFlow<Unit> = _refreshFinished


    override fun onViewCreated() {
        super.onViewCreated()
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch {
            var i = 0
            val response = safeApiCall { contactRepository.getContacts().sortedBy { it.name } }


            response?.let {
                _refreshFinished.emit(Unit)
                val len = response.size - 1
                val tempList: MutableList<BaseItem> = mutableListOf()
                if(len >= 0){
                    val firstTitle = Title(response[0].name[0]!!.toString().toUpperCase())
                    tempList.add(firstTitle)
                    tempList.add(response[0])
                    while(i < len){
                        val name1 = response[i].name!![0]
                        val name2 = response[i + 1].name!![0]
                        if(name1 == name2){
                            tempList.add(response[i + 1])
                        } else {
                            val title = Title(response[i + 1].name[0].toString().toUpperCase())
                            tempList.add(title)
                            tempList.add(response[i + 1])
                        }
                        i++
                    }
                }
                _contacts.value = tempList
                emptyScreen.value = _contacts.value.isNullOrEmpty()
            }
        }
    }

    override fun onDeleteClicked(id: Int) {
        viewModelScope.launch {
            contactRepository.deleteContact(id)
            refresh()
        }
    }

    override fun refresh() {
        getContacts()
    }

}