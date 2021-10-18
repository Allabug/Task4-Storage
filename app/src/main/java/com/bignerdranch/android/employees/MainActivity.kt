package com.bignerdranch.android.employees

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.employees.databinding.ActivityMainBinding
import com.bignerdranch.android.employees.db.EmployeeDatabase
import com.bignerdranch.android.employees.repository.EmployeeRepository
import com.bignerdranch.android.employees.viewmodel.EmployeeViewModel
import com.bignerdranch.android.employees.viewmodel.EmployeeViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setUpViewModel()
        binding.toolbar
    }

    private fun setUpViewModel() {
        val employeeRepository = EmployeeRepository(
            EmployeeDatabase(this)
        )
        val viewModelProviderFactory = EmployeeViewModelProviderFactory(
            application,
            employeeRepository
        )
        employeeViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(EmployeeViewModel::class.java)
    }

}