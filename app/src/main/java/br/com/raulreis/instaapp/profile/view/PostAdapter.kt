package br.com.raulreis.instaapp.profile.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.model.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    var items: List<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position].uri)
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Uri) {
            itemView.findViewById<ImageView>(R.id.imgItemProfileGrid).setImageURI(image)
        }
    }
}