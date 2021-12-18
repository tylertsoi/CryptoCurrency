package com.tyler.cryptocurrency.presentation.currencylist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tyler.cryptocurrency.databinding.CurrencyListFragmentBinding
import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import com.tyler.cryptocurrency.presentation.currencylist.adapter.CurrencyListAdapter
import com.tyler.cryptocurrency.presentation.currencylist.adapter.OnCurrencyInfoClickListener
import com.tyler.cryptocurrency.presentation.currencylist.viewmodel.CurrencyListDisplayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyListFragment(private val onItemClickListener: OnCurrencyInfoClickListener) :
    Fragment() {
    companion object {
        const val tag = "currency_list"
        fun newInstance(itemClickListener: OnCurrencyInfoClickListener) =
            CurrencyListFragment(itemClickListener)
    }

    private val viewModel: CurrencyListDisplayViewModel by viewModels()
    lateinit var binding: CurrencyListFragmentBinding
    lateinit var adapter: CurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrencyListFragmentBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CurrencyListAdapter(mutableListOf()) {
            onItemClickListener.onItemClick(it)
        }
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        observe()
    }

    private fun observe() {
        viewModel.displayList.observe(viewLifecycleOwner, {
            adapter.updateList(it)
            viewModel.showWelcomeMessage.postValue(it.isEmpty())
        })
        viewModel.showWelcomeMessage.observe(viewLifecycleOwner, {
            // hide welcome message if data is not empty
            with(binding){
                welcomeMessage.visibility = if (it) View.VISIBLE else View.GONE
                recyclerView.visibility = if (it) View.GONE else View.VISIBLE
            }
        })
    }

    fun updateList(list: List<CurrencyInfo>) {
        // update current display list
        viewModel.updateList(list)
    }

    fun sort() {
        viewModel.sort()
    }
}