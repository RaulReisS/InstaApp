package br.com.raulreis.instaapp.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.model.Post
import com.bumptech.glide.Glide

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    var items : List<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {

            Glide.with(itemView.context).load(post.photoUrl).into(itemView.findViewById(R.id.imgHomePost))
            Glide.with(itemView.context).load(post.publisher?.photoUrl).into(itemView.findViewById(R.id.imgHomeUser))

            itemView.findViewById<TextView>(R.id.txvHomeCaption).text = post.caption
            itemView.findViewById<TextView>(R.id.txvHomeUsername).text = post.publisher?.name
        }
    }
}