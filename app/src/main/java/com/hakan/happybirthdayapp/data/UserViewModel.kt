package com.hakan.happybirthdayapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import kotlinx.coroutines.launch

class UserViewModel(context: Context) : ViewModel() {

    private var _allUserData: MutableLiveData<List<User>> = MutableLiveData()
    val allUserData: LiveData<List<User>> get() = _allUserData

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(context).UserDao()
        repository = UserRepository(userDao)
    }

    fun readAllData(searchText: String? = null) {
        viewModelScope.launch { _allUserData.value = repository.readAllData(searchText) }
    }

    fun addUser(user: User) {
        viewModelScope.launch { repository.addUser(user) }
    }

    fun updateUser(user: User) {
        viewModelScope.launch { repository.updateUser(user) }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch { repository.deleteUser(user) }
    }


}