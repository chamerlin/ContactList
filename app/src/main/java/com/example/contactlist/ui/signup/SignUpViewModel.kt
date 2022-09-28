package com.example.contactlist.ui.signup

import androidx.lifecycle.*
import com.example.contactlist.data.model.User
import com.example.contactlist.data.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject


class SignUpViewModel (private val repository: UserRepositoryImpl): ViewModel() {

    private val _users: MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>> = _users

    private val _emptyScreen: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = true
    }
    val emptyScreen: LiveData<Boolean> = _emptyScreen

    fun onCreateView() {
        viewModelScope.launch {
            val res = repository.getUsers()
            _users.value = res
            _emptyScreen.value = res.isEmpty()
        }
    }

    fun isValidFirstName(firstName: String): Boolean {
        val PATTERN: Pattern = Pattern.compile("([a-zA-Z]{3,30}\\s*)+")
        return PATTERN.matcher(firstName).matches()
    }

    fun isValidLastName(lastName: String): Boolean {
        val PATTERN: Pattern = Pattern.compile("([a-zA-Z]{3,30})")
        return PATTERN.matcher(lastName).matches()
    }

    fun isValidEmail(email: String): Boolean {
        val PATTERN: Pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return PATTERN.matcher(email).matches()
    }

    fun isValidPassword(password: String, confirmPassword: String): Boolean {
        val PATTERN: Pattern = Pattern.compile("^" + "(?=.*[@#$%^&+=])" +  "(?=\\S+$)" + "(?=.*[A-Z])" + ".{6,}" + "$")
        if(PATTERN.matcher(password).matches() == true && password == confirmPassword) {
            return true
        }
        return false
    }

    fun signUp(user: User): String {
        if(isValidFirstName(user.firstName) == false){
            return "INVALID FIRST NAME"
        }

        if(isValidLastName(user.lastName) == false){
            return "INVALID LAST NAME"
        }

        if(isValidEmail(user.email) == false){
            return "INVALID EMAIL"
        }

        if(isValidPassword(user.password, user.confirmPassword) == false){
            return "INVALID PASSWORD"
        }

        viewModelScope.launch {
            repository.signUp(user)
        }
        return "SUCCESSFUL"
    }

    class Provider(private val repository: UserRepositoryImpl): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel(repository) as T
        }
    }

}