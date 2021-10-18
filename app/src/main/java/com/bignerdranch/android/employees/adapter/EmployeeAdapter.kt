package com.bignerdranch.android.employees.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.employees.databinding.ItemEmployeeInfoBinding
import com.bignerdranch.android.employees.fragments.HomeFragmentDirections
import com.bignerdranch.android.employees.model.Employee


class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    class EmployeeViewHolder(val itemBinding: ItemEmployeeInfoBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.age == newItem.age &&
                    oldItem.profession == newItem.profession
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(
            ItemEmployeeInfoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val currentEmployee = differ.currentList[position]

        holder.itemBinding.tvName.text = currentEmployee.name
        holder.itemBinding.tvAge.text = currentEmployee.age.toString()
        holder.itemBinding.tvProfession.text = currentEmployee.profession

        holder.itemView.setOnClickListener { mView ->
            val direction =
                HomeFragmentDirections.actionHomeFragmentToUpdateFragment(currentEmployee)
            mView.findNavController().navigate(direction)
        }
    }

    override fun getItemCount() = differ.currentList.size

}