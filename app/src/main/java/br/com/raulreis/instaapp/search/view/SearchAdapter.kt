package br.com.raulreis.instaapp.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.model.User
import com.bumptech.glide.Glide

class SearchAdapter (
    private val itemClick: (String) -> Unit
        ) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var items: List<User> = mutableListOf()

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
        fun bind(user: User) {
            Glide.with(itemView.context).load(user.photoUrl).into(itemView.findViewById(R.id.imgSearchUser))
            itemView.findViewById<TextView>(R.id.txvSearchUsername).text = user.name

            itemView.setOnClickListener {
                if (user.uuid != null)
                    itemClick.invoke(user.uuid)
            }
        }
    }
}