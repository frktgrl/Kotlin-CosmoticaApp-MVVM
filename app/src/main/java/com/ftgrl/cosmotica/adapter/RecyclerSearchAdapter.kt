package com.ftgrl.cosmotica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.databinding.SearchRecyclerViewBinding

class RecyclerSearchAdapter : RecyclerView.Adapter<RecyclerSearchAdapter.SearchViewHolder>() {

    private var products: List<Product> = listOf()

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    class SearchViewHolder(private val binding: SearchRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }
}
