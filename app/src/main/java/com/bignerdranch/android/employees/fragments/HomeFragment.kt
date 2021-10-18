package com.bignerdranch.android.employees.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.employees.MainActivity
import com.bignerdranch.android.employees.R
import com.bignerdranch.android.employees.adapter.EmployeeAdapter
import com.bignerdranch.android.employees.databinding.FragmentHomeBinding
import com.bignerdranch.android.employees.model.Employee
import com.bignerdranch.android.employees.sqldb.EmployeeDatabaseHelper
import com.bignerdranch.android.employees.viewmodel.EmployeeViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var employeeAdapter: EmployeeAdapter
    private val sp by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
    private lateinit var dbHelper: EmployeeDatabaseHelper
    private val touchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val switchOn = sp.getBoolean("cursor", false)
            val sort = sp.getString("sort", "")
            if (switchOn) {
                dbHelper.deleteOneEmployee(employeeAdapter.differ.currentList[position].id.toString())
                setSortedListSQLite(sort)
            } else {
                employeeViewModel.deleteEmployee(employeeAdapter.differ.currentList[position])
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        dbHelper = EmployeeDatabaseHelper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity?.title = getString(R.string.title_employees)
        val switchOn = sp.getBoolean("cursor", false)
        employeeViewModel = (activity as MainActivity).employeeViewModel
        if (switchOn) {
            setUpRecyclerViewWithSQLite()
        } else {
            setUpRecyclerViewWithRoom()
        }
        binding.flAddButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newEmployerFragment)
        }
    }

    private fun setUpRecyclerViewWithSQLite() {
        val sort = sp.getString("sort", "")
        employeeAdapter = EmployeeAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = employeeAdapter
            val itemTouchHelper = ItemTouchHelper(touchHelper)
            itemTouchHelper.attachToRecyclerView(this)
        }
        activity?.let {
            setSortedListSQLite(sort)
        }
    }

    private fun setUpRecyclerViewWithRoom() {
        val sort = sp.getString("sort", "")
        employeeAdapter = EmployeeAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = employeeAdapter
            val itemTouchHelper = ItemTouchHelper(touchHelper)
            itemTouchHelper.attachToRecyclerView(this)
        }
        activity?.let {
            setSortedListRoom(sort)
        }
    }

    private fun setSortedListSQLite(sort: String?) {
        when (sort) {
            "sort_name" -> {
                val employeesList = dbHelper.getAllEmployeesSQLByName()
                employeeAdapter.differ.submitList(employeesList)
                updateUI(employeesList)
            }
            "sort_age" -> {
                val employeesList = dbHelper.getAllEmployeesSQLByAge()
                employeeAdapter.differ.submitList(employeesList)
                updateUI(employeesList)
            }
            "sort_prof" -> {
                val employeesList = dbHelper.getAllEmployeesSQLByProf()
                employeeAdapter.differ.submitList(employeesList)
                updateUI(employeesList)
            }
            else -> {
                val employeesList = dbHelper.getAllEmployeesSQL()
                employeeAdapter.differ.submitList(employeesList)
                updateUI(employeesList)
            }
        }
    }

    private fun setSortedListRoom(sort: String?) {
        when (sort) {
            "sort_name" -> employeeViewModel.getEmployeesByName()
                .observe(viewLifecycleOwner, { employee ->
                    employeeAdapter.differ.submitList(employee)
                    updateUI(employee)
                })
            "sort_age" -> employeeViewModel.getEmployeesByAge()
                .observe(viewLifecycleOwner, { employee ->
                    employeeAdapter.differ.submitList(employee)
                    updateUI(employee)
                })
            "sort_prof" -> employeeViewModel.getEmployeesByProf()
                .observe(viewLifecycleOwner, { employee ->
                    employeeAdapter.differ.submitList(employee)
                    updateUI(employee)
                })
            else -> employeeViewModel.getAllEmployee().observe(viewLifecycleOwner, { employee ->
                employeeAdapter.differ.submitList(employee)
                updateUI(employee)
            })
        }
    }

    private fun updateUI(employee: List<Employee>) {
        if (employee.isNotEmpty()) {
            binding.emptyImageview.visibility = View.GONE
            binding.noData.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.emptyImageview.visibility = View.VISIBLE
            binding.noData.visibility = View.VISIBLE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_filter -> view?.findNavController()
                ?.navigate(R.id.action_homeFragment_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}