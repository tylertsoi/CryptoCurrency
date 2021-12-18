package com.tyler.cryptocurrency.presentation.currencylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tyler.cryptocurrency.databinding.CurrencyItemBinding
import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import com.tyler.cryptocurrency.presentation.currencylist.fragment.CurrencyListFragment

/**
 * [RecyclerView.Adapter] used in [CurrencyListFragment]
 */
class CurrencyListAdapter internal constructor(
    private var currencyInfo: MutableList<CurrencyInfo> = mutableListOf(),
    var onItemClick: (CurrencyInfo) -> Unit
) : RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {
    // Inner classes
    inner class ViewHolder constructor(binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val shortName: TextView = binding.shortName
        val name: TextView = binding.name
        val symbol: TextView = binding.symbol
        val arrow: ImageView = binding.arrow

        fun bind(currencyInfo: CurrencyInfo, clickListener: (CurrencyInfo) -> Unit) {
            shortName.text = currencyInfo.id.substring(0, 1)
            name.text = currencyInfo.name
            symbol.text = currencyInfo.symbol

            itemView.setOnClickListener {
                clickListener(currencyInfo)
            }
        }
    }

    fun updateList(list: List<CurrencyInfo>) {
        this.currencyInfo = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            CurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = currencyInfo[position]
        holder.bind(info, onItemClick)
    }

    override fun getItemCount(): Int {
        return currencyInfo.size
    }
}