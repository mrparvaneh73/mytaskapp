package com.example.usertaskapp.ui.loginandregister.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usertaskapp.data.localdatabase.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application){
private val _userID = MutableLiveData<Int>()
    val userID:LiveData<Int> = _userID



    val inputUsername = MutableLiveData<String?>()

    val inputPassword = MutableLiveData<String?>()

    private val _navigatetoRegister = MutableLiveData<Boolean>()
        val navigatetoRegister:LiveData<Boolean> = _navigatetoRegister

    private val _navigatetoHome = MutableLiveData<Boolean>()
        val navigatetoHome:LiveData<Boolean> = _navigatetoHome

    private val _errotoast = MutableLiveData<Boolean>()
        val errotoast:LiveData<Boolean> = _errotoast

    private val _errotoastUsername = MutableLiveData<Boolean>()
        val errotoastUsername:LiveData<Boolean> = _errotoastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()
        val errorToastInvalidPassword: LiveData<Boolean> = _errorToastInvalidPassword


    fun loginButton() {
        if (inputUsername.value == null || inputPassword.value == null) {
            _errotoast.value = true
        } else {
            viewModelScope.launch {
                val usersNames = repository.getUserName(inputUsername.value!!)

                if (usersNames != null) {
                    if(usersNames.passwrd == inputPassword.value){

                        _userID.postValue(usersNames.userId)
                        inputUsername.value = null
                        inputPassword.value = null
                        _navigatetoHome.value = true
                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errotoastUsername.value = true
                }
            }
        }
    }



    fun signUP() {
        _navigatetoRegister.value = true
    }

    fun doneNavigatingRegiter() {
        _navigatetoRegister.value = false
    }

    fun doneNavigatingToHomeFragment() {
        _navigatetoHome.value = false
    }


    fun donetoast() {
        _errotoast.value = false

    }


    fun donetoastErrorUsername() {
        _errotoastUsername .value = false

    }

    fun donetoastInvalidPassword() {
        _errorToastInvalidPassword .value = false

    }


}