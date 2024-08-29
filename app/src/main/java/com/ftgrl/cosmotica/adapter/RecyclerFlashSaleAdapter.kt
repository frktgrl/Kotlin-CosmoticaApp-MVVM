package com.ftgrl.cosmotica.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.data.entity.Product
import com.squareup.picasso.Picasso


class RecyclerFlashSaleAdapter(private var flashsaleList: MutableList<Product>) : RecyclerView.Adapter<RecyclerFlashSaleAdapter.RecyclerFlashSaleViewHolder>() {

    class RecyclerFlashSaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val product_name: TextView = itemView.findViewById(R.id.product_name)
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val price_text : TextView = itemView.findViewById(R.id.price_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerFlashSaleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flashsale_recycler_view, parent, false)
        return RecyclerFlashSaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerFlashSaleViewHolder, position: Int) {

        val product = flashsaleList[position]

        // Title'ı 15 karakterle sınırlandırın
        val truncatedTitle = if (product.title.length > 15) {
            product.title.substring(0, 15) + "..."
        } else {
            product.title
        }

        holder.product_name.text = truncatedTitle
        holder.price_text.text = product.price.toString()

        Picasso.get()
            .load(product.thumbnail)
            .placeholder(R.drawable.ic_favorite_border)
            .error(R.drawable.favorite_icon)
            .into(holder.productImage)

    }

    override fun getItemCount(): Int {
        return flashsaleList.size
    }

    // Yeni updateProducts fonksiyonu
    fun updateProducts(newProducts: MutableList<Product>) {
        flashsaleList.clear()
        flashsaleList.addAll(newProducts)
        notifyDataSetChanged() // RecyclerView'ı günceller
    }
}
