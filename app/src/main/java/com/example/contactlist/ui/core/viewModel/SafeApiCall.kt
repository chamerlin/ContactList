package com.example.contactlist.ui.core.viewModel

import kotlinx.coroutines.flow.SharedFlow

interface SafeApiCall {
    val apiError2: SharedFlow<String>

    suspend fun <T> safeApiCall2(apiCall: suspend () -> T?): T?
}