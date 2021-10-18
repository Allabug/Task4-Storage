package com.bignerdranch.android.employees.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.employees.repository.EmployeeRepository

class EmployeeViewModelProviderFactory(
    val app: Application,
    private val employeeRepository: EmployeeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeeViewModel(app, employeeRepository) as T
    }
}