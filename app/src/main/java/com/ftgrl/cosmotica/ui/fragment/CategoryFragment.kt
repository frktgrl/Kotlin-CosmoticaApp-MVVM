package com.ftgrl.cosmotica.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.adapter.RecyclerProductAdapter
import com.ftgrl.cosmotica.databinding.FragmentCategoryBinding
import com.ftgrl.cosmotica.ui.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()
    private val args: CategoryFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        val navController = findNavController()
        val adapter = RecyclerProductAdapter(navController)
        binding.productAdapter = adapter

        var categoryName = args.categoryName

        if (categoryName == "All Products") {

            viewModel.getAllProduct()
        }

        binding.toolbarTitle = categoryName

        // ViewModel'den verileri al ve RecyclerView'a bağla
        viewModel.getProductsByCategory(categoryName)


        // ViewModel'deki ürün listesi güncellendiğinde adapter'ı güncelle
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        return binding.root
    }
}
