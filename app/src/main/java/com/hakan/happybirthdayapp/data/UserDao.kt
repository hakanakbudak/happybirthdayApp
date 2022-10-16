package com.hakan.happybirthdayapp.data

import androidx.room.*
import java.util.concurrent.Flow
import android.app.Person as Person1


@Dao
interface UserDao {

    @Query(value = "SELECT * FROM users ORDER BY name ASC")
    suspend fun readAllData(): List<User>

    @Query(value = "SELECT * FROM users where name like '%' + :searchText + '%' or surname like '%' + :searchText + '%'  or date like '%' + :searchText + '%'   or departmentText like '%' + :searchText + '%'    or descriptor like '%' + :searchText + '%' ORDER BY name ASC")
    suspend fun readSearchAllData(searchText: String? = null): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Update()
    suspend fun updateUser(user: User)

    @Delete()
    suspend fun deleteUser(user: User)


    /** RoomDatabase içerisinde "users" tablosunda "name" ve "surname" araması olacak.
     * @author Hakan Akbudak
     * @since 06.09.2022
     **/

    @Query("SELECT * FROM users WHERE name LIKE :searchQuery OR surname LIKE :searchQuery")
    fun searchTextDatabase(searchQuery: String): List<User>
}