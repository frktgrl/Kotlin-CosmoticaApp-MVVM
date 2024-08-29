package com.ftgrl.cosmotica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.data.entity.Category
import com.ftgrl.cosmotica.databinding.CategoryRecyclerViewBinding
import com.ftgrl.cosmotica.ui.fragment.HomeFragmentDirections
import com.ftgrl.cosmotica.ui.fragment.SignInFragmentDirections

class RecyclerCategoryAdapter(
    private val navController: NavController,
    private var categoryList: List<Category>
) : RecyclerView.Adapter<RecyclerCategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: CategoryRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.categoryName.text = category.name
            binding.categoryCard.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(category.name)
                navController.navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: CategoryRecyclerViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.category_recycler_view,
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return if (categoryList.size > 8) 8 else categoryList.size
    }

    fun updateData(newCategories: List<Category>) {
        categoryList = newCategories
        notifyDataSetChanged()
    }
}



