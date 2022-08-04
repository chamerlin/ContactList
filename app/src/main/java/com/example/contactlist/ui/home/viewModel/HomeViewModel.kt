package com.example.contactlist.ui.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactlist.data.model.BaseItem
import kotlinx.coroutines.flow.SharedFlow

interface HomeViewModel {
    val contacts: LiveData<List<BaseItem>>
    val emptyScreen: MutableLiveData<Boolean>
    val refreshFinished: SharedFlow<Unit>
    fun onDeleteClicked(id: Int)
    fun refresh()
}