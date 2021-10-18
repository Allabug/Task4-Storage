package com.bignerdranch.android.employees.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.bignerdranch.android.employees.MainActivity
import com.bignerdranch.android.employees.R
import com.bignerdranch.android.employees.databinding.ActivityMainBinding
import com.bignerdranch.android.employees.databinding.FragmentNewEmployeeBinding
import com.bignerdranch.android.employees.model.Employee
import com.bignerdranch.android.employees.sqldb.EmployeeDatabaseHelper
import com.bignerdranch.android.employees.toast
import com.bignerdranch.android.employees.viewmodel.EmployeeViewModel
import com.google.android.material.snackbar.Snackbar


class NewEmployerFragment : Fragment(R.layout.fragment_new_employee) {

    private var _binding: FragmentNewEmployeeBinding? = null
    private val binding get() = _binding!!
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var mView: View
    private val sp by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
    private lateinit var dbHelper: EmployeeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        dbHelper = EmployeeDatabaseHelper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewEmployeeBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity?.title = getString(R.string.add_new_employee)
        employeeViewModel = (activity as MainActivity).employeeViewModel
        mView = view
        val switchOn = sp.getBoolean("cursor", false)

        binding.updateButton.setOnClickListener {
            if (switchOn) {
                saveEmployeeSQL(mView)
            } else {
                saveEmployee(mView)
            }
        }
    }

    private fun saveEmployee(view: View) {
        val employeeName = binding.etNameInput.text.toString().trim()
        val employeeAge = binding.etAgeUpdate.text.toString().trim()
        val employeeProfession = binding.etProfessionInput.text.toString().trim()

        if (employeeName.isNotEmpty() && employeeAge.isNotEmpty() && employeeProfession.isNotEmpty()) {
            val age = employeeAge.toInt()
            val employee = Employee(0, employeeName, age, employeeProfession)

            employeeViewModel.addEmployee(employee)
            Snackbar.make(
                view,
                "Employee save successfully",
                Snackbar.LENGTH_SHORT
            ).show()

            view.findNavController().navigate(R.id.action_newEmployerFragment_to_homeFragment)
        } else {
            activity?.toast("Please fill in all fields")
        }
    }

    private fun saveEmployeeSQL(view: View) {
        val employeeName = binding.etNameInput.text.toString().trim()
        val employeeAge = binding.etAgeUpdate.text.toString().trim()
        val employeeProfession = binding.etProfessionInput.text.toString().trim()

        if (employeeName.isNotEmpty() && employeeAge.isNotEmpty() && employeeProfession.isNotEmpty()) {
            val age = employeeAge.toInt()
            val dbHelper = EmployeeDatabaseHelper(context)
            dbHelper.addEmployee(employeeName, age, employeeProfession)
            view.findNavController().navigate(R.id.action_newEmployerFragment_to_homeFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val switchOn = sp.getBoolean("cursor", false)
        when (item.itemId) {
            R.id.menu_save -> if (switchOn) {
                saveEmployeeSQL(mView)
            } else {
                saveEmployee(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.new_employee_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}