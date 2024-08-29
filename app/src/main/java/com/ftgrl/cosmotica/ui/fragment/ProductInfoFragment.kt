package com.ftgrl.cosmotica.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.databinding.FragmentProductInfoBinding
import com.ftgrl.cosmotica.ui.viewmodel.ProductInfoViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductInfoFragment : Fragment() {

    private lateinit var binding: FragmentProductInfoBinding
    private val viewModel: ProductInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_info, container, false)
        binding.fragment = this

        // Argümanları al ve binding'e ata
        val product = arguments?.getParcelable<Product>("product")
        binding.product = product

        Picasso.get()
            .load(product?.thumbnail)
            .placeholder(R.drawable.favorite_icon)
            .error(R.drawable.home_icon)
            .into(binding.productImage)


        return binding.root
    }

    fun addFavorite(product: Product) {
        product?.id?.let { id ->
            viewModel.addFavorite(id)

        }
    }

}

