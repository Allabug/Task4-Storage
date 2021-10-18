package com.bignerdranch.android.employees.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.employees.model.Employee

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEmployee(employee: Employee)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employees")
    fun getAllEmployees(): LiveData<List<Employee>>

    @Query("SELECT * FROM employees ORDER BY name ASC")
    fun getEmployeesByName(): LiveData<List<Employee>>

    @Query("SELECT * FROM employees ORDER BY age ASC")
    fun getEmployeesByAge(): LiveData<List<Employee>>

    @Query("SELECT * FROM employees ORDER BY profession ASC")
    fun getEmployeesByProf(): LiveData<List<Employee>>
}