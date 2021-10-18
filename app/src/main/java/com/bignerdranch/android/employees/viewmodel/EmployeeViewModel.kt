package com.bignerdranch.android.employees.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.employees.model.Employee
import com.bignerdranch.android.employees.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(
    app: Application,
    private val employeeRepository: EmployeeRepository
) : AndroidViewModel(app) {

    fun addEmployee(employee: Employee) = viewModelScope.launch {
        employeeRepository.addEmployee(employee)
    }

    fun updateEmployee(employee: Employee) = viewModelScope.launch {
        employeeRepository.updateEmployee(employee)
    }

    fun deleteEmployee(employee: Employee) = viewModelScope.launch {
        employeeRepository.deleteEmployee(employee)
    }

    fun getAllEmployee() = employeeRepository.getAllEmployees()

    fun getEmployeesByName() = employeeRepository.getEmployeesByName()
    fun getEmployeesByAge() = employeeRepository.getEmployeesByAge()
    fun getEmployeesByProf() = employeeRepository.getEmployeesByProf()
}
