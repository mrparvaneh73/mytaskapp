package com.example.usertaskapp.ui.home.dialog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usertaskapp.data.localdatabase.Repository

class CustomDialogViewModelFactory(private  val repository: Repository,
                                   private val application: Application
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CustomDialogViewModel::class.java)) {
            return CustomDialogViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}