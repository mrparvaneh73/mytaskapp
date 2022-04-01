package com.example.usertaskapp.ui.loginandregister.register

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usertaskapp.data.localdatabase.Repository
import com.example.usertaskapp.data.localdatabase.model.User
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application), Observable {

    val inputFirstName = MutableLiveData<String?>()

    @Bindable
    val inputLastName = MutableLiveData<String?>()

    @Bindable
    val inputUsername = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableLiveData<String?>()
    private val _navigateto = MutableLiveData<Boolean>()
    val navigateto: LiveData<Boolean> = _navigateto

    private val _errotoast = MutableLiveData<Boolean>()
    val errotoast: LiveData<Boolean> = _errotoast

    private val _errotoastUsername = MutableLiveData<Boolean>()
    val errotoastUsername: LiveData<Boolean> = _errotoastUsername

    fun saveNewUser() {

        if (inputFirstName.value == null || inputLastName.value == null || inputUsername.value == null || inputPassword.value == null) {
            _errotoast.value = true
        } else {
            viewModelScope.launch {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    _errotoastUsername.value = true
                } else {
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val email = inputUsername.value!!
                    val password = inputPassword.value!!

                    insert(User(0, firstName, lastName, email, password))

                    inputFirstName.value = null
                    inputLastName.value = null
                    inputUsername.value = null
                    inputPassword.value = null
                    _navigateto.value = true

                }


            }


        }


    }

    private fun insert(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }


    fun doneNavigating() {
        _navigateto.value = false
    }

    fun donetoast() {
        _errotoast.value=false
    }

    fun donetoastUserName() {
        _errotoastUsername.value = false

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}