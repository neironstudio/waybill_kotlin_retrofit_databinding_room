package com.tetron.waybill.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tetron.waybill.databinding.RefuelingItemBinding
import com.tetron.waybill.model.waybill.Refueling
import com.tetron.waybill.viewmodel.FuelViewModel

class RefuelingsListAdapter(private val viewModel: FuelViewModel) :
    ListAdapter<Refueling, RefuelingsListAdapter.ViewHolder>(RefuelingDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RefuelingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: FuelViewModel, item: Refueling) {
            binding.fuelViewModel = viewModel
            binding.refueling = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RefuelingItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class RefuelingDiffCallback : DiffUtil.ItemCallback<Refueling>() {
    override fun areItemsTheSame(oldItem: Refueling, newItem: Refueling): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Refueling, newItem: Refueling): Boolean {
        return oldItem == newItem
    }
}