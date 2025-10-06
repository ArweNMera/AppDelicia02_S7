package com.delicia.app.ui.catalog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.delicia.app.core.Result
import com.delicia.app.databinding.ActivityCatalogBinding
import com.delicia.app.domain.usecase.AddToCartUseCase
import com.delicia.app.ui.cart.CartActivity
import com.google.android.material.snackbar.Snackbar

class CatalogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding
    private val viewModel: CatalogViewModel by viewModels()
    private lateinit var adapter: ProductAdapter
    private val addToCartUseCase = AddToCartUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter { product ->
            addToCartUseCase(product)
            Snackbar.make(binding.root, "${product.name} añadido al carrito", Snackbar.LENGTH_SHORT)
                .setAction("VER") {
                    startActivity(Intent(this, CartActivity::class.java))
                }
                .show()
        }
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productsRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.catalogState.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.productsRecyclerView.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.productsRecyclerView.visibility = View.VISIBLE
                    adapter.submitList(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.cartFab.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}