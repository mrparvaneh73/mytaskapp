package com.example.usertaskapp.ui.loginandregister.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.usertaskapp.R
import com.example.usertaskapp.data.localdatabase.Repository
import com.example.usertaskapp.data.localdatabase.UserDatabase
import com.example.usertaskapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login, container, false
        )

        viewmodelprovider()

        binding.myLoginViewModel = loginViewModel

        binding.lifecycleOwner = this

        navigationToRegister()

        toastError()

        navigationToHome()



        return binding.root
    }



    private fun navigateToRegisterUser() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)

    }


    private fun navigateToHomeFragment() {

        loginViewModel.userID.observe(viewLifecycleOwner) {

            val action = LoginFragmentDirections.actionLoginFragmentToMainActivity2(it)

            action.userid = it

            findNavController().navigate(action)

        }


    }

    private fun viewmodelprovider(){
        val application = requireNotNull(this.activity).application

        val dao = UserDatabase.getInstance(application).userDao

        val repository = Repository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    private fun navigationToRegister(){
        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                navigateToRegisterUser()
                loginViewModel.doneNavigatingRegiter()
            }
        })
    }

    private fun toastError(){
        loginViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(
                    requireContext(),
                    "User doesnt exist,please Register!",
                    Toast.LENGTH_SHORT
                ).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT)
                    .show()
                loginViewModel.donetoastInvalidPassword()
            }
        })
    }

    private fun navigationToHome(){
        loginViewModel.navigatetoHome.observe(viewLifecycleOwner, Observer {  navigationToHome ->
            if (navigationToHome == true) {
                navigateToHomeFragment()
                loginViewModel.doneNavigatingToHomeFragment()
            }
        })
    }
}