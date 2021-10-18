package com.bignerdranch.android.employees.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.employees.db.EmployeeDatabase
import com.bignerdranch.android.employees.model.Employee

class EmployeeRepository(private val db: EmployeeDatabase) {

    suspend fun addEmployee(employee: Employee) = db.getEmployeeDao().addEmployee(employee)
    suspend fun updateEmployee(employee: Employee) = db.getEmployeeDao().updateEmployee(employee)
    suspend fun deleteEmployee(employee: Employee) = db.getEmployeeDao().deleteEmployee(employee)
    fun getAllEmployees() = db.getEmployeeDao().getAllEmployees()

    fun getEmployeesByName() = db.getEmployeeDao().getEmployeesByName()
    fun getEmployeesByAge() = db.getEmployeeDao().getEmployeesByAge()
    fun getEmployeesByProf() = db.getEmployeeDao().getEmployeesByProf()

}