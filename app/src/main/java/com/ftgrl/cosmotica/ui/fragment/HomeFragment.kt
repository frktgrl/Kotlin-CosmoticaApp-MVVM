package com.ftgrl.cosmotica.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.adapter.RecyclerCategoryAdapter
import com.ftgrl.cosmotica.adapter.RecyclerFlashSaleAdapter
import com.ftgrl.cosmotica.adapter.RecyclerProductAdapter
import com.ftgrl.cosmotica.adapter.RecyclerSearchAdapter
import com.ftgrl.cosmotica.data.entity.Category
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.databinding.FragmentHomeBinding
import com.ftgrl.cosmotica.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var flashSaleAdapter: RecyclerFlashSaleAdapter
    private lateinit var searchAdapter: RecyclerSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHome)

        binding.searchTitle = "Search Product"

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_fragment_menu, menu)
                val item = menu.findItem(R.id.action_search)
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(this@HomeFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        val navController = findNavController()
        val recyclerCategoryAdapter = RecyclerCategoryAdapter(navController, emptyList())
        binding.categoryAdapter = recyclerCategoryAdapter


        flashSaleAdapter = RecyclerFlashSaleAdapter(mutableListOf())
        binding.flashSaleAdapter = flashSaleAdapter

        searchAdapter = RecyclerSearchAdapter()
        binding.searchAdapter= searchAdapter


        viewModel.fetchCategories()
        viewModel.fetchCart()



        viewModel.categoryList.observe(viewLifecycleOwner) { categories ->
            if (categories != null && categories.isNotEmpty()) {
                val updatedCategories = mutableListOf(Category("all", "All Products", "")) // Example of Category creation
                updatedCategories.addAll(categories)
                recyclerCategoryAdapter.updateData(updatedCategories)
                Log.d("Category List", "Kategoriler başarıyla yüklendi.")
            } else {
                Log.d("Category List", "Kategori bulunamadı.")
            }
        }


        viewModel.carts.observe(viewLifecycleOwner) { carts ->
            val allProducts = carts.flatMap { it.products }.toMutableList()
            flashSaleAdapter.updateProducts(allProducts)
            Log.d("Flash Sale List", "Flash sale ürünleri başarıyla yüklendi.")
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { products ->
            if (products != null && products.isNotEmpty()) {
                searchAdapter.updateProducts(products)
                Log.d("Search Results", "Arama sonuçları başarıyla yüklendi.")
            } else {
                Log.d("Search Results", "Arama sonucu bulunamadı.")
                searchAdapter.updateProducts(emptyList()) // Sonuç yoksa listeyi temizle
            }
        }




        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.searchProduct(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.searchProduct(newText)
        return true
    }
}
