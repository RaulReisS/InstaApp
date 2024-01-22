package br.com.raulreis.instaapp.profile.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.model.Post
import com.bumptech.glide.Glide

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
        holder.bind(items[position].photoUrl)
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photoUrl: String?) {
            Glide.with(itemView.context).load(photoUrl).into(itemView.findViewById(R.id.imgItemProfileGrid))
        }
    }
}