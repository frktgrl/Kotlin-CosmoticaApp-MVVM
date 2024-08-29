package com.ftgrl.cosmotica.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.adapter.RecyclerFavoriteAdapter
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.databinding.FragmentFavoriteBinding
import com.ftgrl.cosmotica.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: RecyclerFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        adapter = RecyclerFavoriteAdapter(emptyList())
        binding.favoriteAdapter= adapter


        viewModel.getFavoriteProducts()


        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.favoriteProducts.observe(viewLifecycleOwner) { products ->
            if (products != null && products.isNotEmpty()) {
                Log.d("FavoriteFragment", "Gelen ürünler: $products")
                adapter.updateProducts(products) // Adapter'ı güncelle
            } else {
                Log.d("FavoriteFragment", "Ürün listesi boş veya null")
            }
        }
    }


}



