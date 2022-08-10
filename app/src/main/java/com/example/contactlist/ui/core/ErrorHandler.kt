package com.example.contactlist.ui.core

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.contactlist.ui.core.viewModel.SafeApiCall

interface ErrorHandler {
    fun setupErrorHandler(view: View, safeApiViewModel: SafeApiCall, lifecycleOwner: LifecycleOwner)
}