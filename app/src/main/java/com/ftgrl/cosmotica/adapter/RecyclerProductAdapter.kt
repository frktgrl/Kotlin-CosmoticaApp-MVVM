package com.ftgrl.cosmotica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.databinding.ProductRecyclerViewBinding
import com.ftgrl.cosmotica.ui.fragment.CategoryFragmentDirections
import com.squareup.picasso.Picasso

class RecyclerProductAdapter(private val navController: NavController) : ListAdapter<Product, RecyclerProductAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ProductRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), navController)
    }

    class CategoryViewHolder(private val binding: ProductRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, navController: NavController) {
            binding.product = product
            binding.priceText.text = product.price.toString()
            binding.executePendingBindings()

            Picasso.get()
                .load(product.thumbnail)
                .placeholder(R.drawable.favorite_icon)
                .error(R.drawable.home_icon)
                .into(binding.productImage)

            binding.productCard.setOnClickListener {
                val action = CategoryFragmentDirections.actionCategoryFragmentToProductInfoFragment(product)
                navController.navigate(action)
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}



