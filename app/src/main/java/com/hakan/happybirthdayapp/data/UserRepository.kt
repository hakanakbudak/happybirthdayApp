package com.hakan.happybirthdayapp.data

class UserRepository(private val userDao: UserDao) {

    suspend fun readAllData(searchText: String? = null): List<User> {
        return if (searchText.isNullOrEmpty()) {
            userDao.readAllData()
        } else {
            userDao.readSearchAllData(searchText)
        }
    }

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

}
