package br.com.raulreis.instaapp.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.model.UserAuth

class SearchAdapter (
    private val itemClick: (String) -> Unit
        ) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var items: List<UserAuth> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserAuth) {
            itemView.findViewById<ImageView>(R.id.imgSearchUser).setImageURI(user.photoUri)
            itemView.findViewById<TextView>(R.id.txvSearchUsername).text = user.name

            itemView.setOnClickListener {
                itemClick.invoke(user.uuid)
            }
        }
    }
}