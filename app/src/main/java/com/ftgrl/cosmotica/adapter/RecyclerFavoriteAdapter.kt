package com.ftgrl.cosmotica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.databinding.FavoriteRecyclerViewBinding
import com.squareup.picasso.Picasso


class RecyclerFavoriteAdapter(private var productList: List<Product>) : RecyclerView.Adapter<RecyclerFavoriteAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = FavoriteRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateProducts(newProducts: List<Product>) {
        productList = newProducts
        notifyDataSetChanged()
    }

    class ProductViewHolder(private val binding: FavoriteRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {



            // Ürün resmini yüklemek için Picasso veya Glide kullanabilirsiniz
            Picasso.get()
                .load(product.thumbnail)
                .placeholder(R.drawable.ic_favorite_border)
                .error(R.drawable.favorite_icon)
                .into(binding.productImage)

            binding.productTitle.text = product.title
            binding.productPrice.text = "$${product.price}"
            binding.productStock.text = product.stock.toString()

            // CardView'ı tıklanabilir hale getir
            binding.root.setOnClickListener {
                // Ürünün tıklanmasıyla yapılacak işlemler
            }
        }
    }
}



