package br.com.raulreis.instaapp.post.view

import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.instaapp.R

class PictureAdapter(private val onClick : (Uri) -> Unit) : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    var items: List<Uri> = mutableListOf()
    var prevView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.Q)
        fun bind(image: Uri) {
            val bitmap = itemView.context.contentResolver.loadThumbnail(image, Size(200,200), null)
            itemView.findViewById<ImageView>(R.id.imgItemProfileGrid).setImageBitmap(bitmap)
            itemView.setOnClickListener {
                onClick.invoke(image)
                itemView.findViewById<LinearLayout>(R.id.selectedFilter).visibility = View.VISIBLE
                if (prevView != null) {
                    prevView?.findViewById<LinearLayout>(R.id.selectedFilter)?.visibility = View.GONE
                }
                prevView = itemView
            }
            if (prevView == null) {
                prevView = itemView
                itemView.findViewById<LinearLayout>(R.id.selectedFilter).visibility = View.VISIBLE
            }
        }
    }
}