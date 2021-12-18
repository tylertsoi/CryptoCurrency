package com.tyler.cryptocurrency.presentation.currencylist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.tyler.cryptocurrency.databinding.DemoActivityBinding
import com.tyler.cryptocurrency.domain.entities.CurrencyInfo
import com.tyler.cryptocurrency.presentation.currencylist.adapter.OnCurrencyInfoClickListener
import com.tyler.cryptocurrency.presentation.currencylist.fragment.CurrencyListFragment
import com.tyler.cryptocurrency.presentation.currencylist.viewmodel.DemoActivityViewModel
import com.tyler.cryptocurrency.util.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class DemoActivity : AppCompatActivity(), OnCurrencyInfoClickListener {
    private val viewModel: DemoActivityViewModel by viewModels()
    lateinit var binding: DemoActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DemoActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.fragmentContainerView.id,
                    CurrencyListFragment.newInstance(this),
                    CurrencyListFragment.tag
                )
                .commitNow()
        }
        initListeners()
    }

    override fun onItemClick(currencyInfo: CurrencyInfo) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(
                this@DemoActivity,
                "onItemClick ${currencyInfo.id}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initListeners() {
        with(binding) {
            // click with debounce to prevent double clicks (disabled)
            // runBlocking wait until process is finish
            loadDataButton.clickWithDebounce(0, action = {
                runBlocking {
                    fetchCurrency()
                }
            })
            sortDataButton.clickWithDebounce(0, action = {
                runBlocking {
                    supportFragmentManager.findFragmentByTag(CurrencyListFragment.tag)?.let {
                        (it as CurrencyListFragment).sort()
                    }
                }
            })
        }
    }

    private fun fetchCurrency() {
        viewModel.fetchCurrencyList().runCatching {
            CoroutineScope(Dispatchers.IO).launch {
                collect { list ->
                    supportFragmentManager.findFragmentByTag(CurrencyListFragment.tag)?.let {
                        (it as CurrencyListFragment).updateList(list)
                    }
                }
            }
        }.onFailure {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    this@DemoActivity,
                    "something failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}