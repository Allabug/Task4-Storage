package com.bignerdranch.android.employees.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.bignerdranch.android.employees.MainActivity
import com.bignerdranch.android.employees.R
import com.bignerdranch.android.employees.databinding.FragmentUpdateBinding
import com.bignerdranch.android.employees.model.Employee
import com.bignerdranch.android.employees.sqldb.EmployeeDatabaseHelper
import com.bignerdranch.android.employees.toast
import com.bignerdranch.android.employees.viewmodel.EmployeeViewModel


class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateFragmentArgs by navArgs()
    private lateinit var currentEmployee: Employee
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var dbHelper: EmployeeDatabaseHelper
    private val sp by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity?.title = getString(R.string.title_update_employee)
        dbHelper = EmployeeDatabaseHelper(context)
        employeeViewModel = (activity as MainActivity).employeeViewModel
        val switchOn = sp.getBoolean("cursor", false)
        currentEmployee = args.employee!!

        binding.etNameUpdate.setText(currentEmployee.name)
        binding.etAgeUpdate.setText(currentEmployee.age.toString())
        binding.etProfessionUpdate.setText(currentEmployee.profession)

        binding.updateButton.setOnClickListener {
            val name = binding.etNameUpdate.text.toString().trim()
            val age = binding.etAgeUpdate.text.toString().trim()
            val profession = binding.etProfessionUpdate.text.toString().trim()
            val id = currentEmployee.id.toString()

            if (name.isNotEmpty() && age.isNotEmpty() && profession.isNotEmpty()) {
                val employee = Employee(currentEmployee.id, name, age.toInt(), profession)
                if (switchOn) {
                    dbHelper.updateData(id, name, age.toInt(), profession)
                } else {
                    employeeViewModel.updateEmployee(employee)
                }
                view.findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            } else {
                activity?.toast("Please fill all fields!")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteEmployee()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteEmployee() {
        val switchOn = sp.getBoolean("cursor", false)
        AlertDialog.Builder(activity).apply {
            setTitle("Delete employee")
            setMessage("Are you sure want to delete this employee?")
            setPositiveButton("DELETE") { _, _ ->
                if (switchOn) {
                    dbHelper.deleteOneEmployee(currentEmployee.id.toString())
                } else {
                    employeeViewModel.deleteEmployee(currentEmployee)
                }
                view?.findNavController()?.navigate(R.id.action_updateFragment_to_homeFragment)
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}