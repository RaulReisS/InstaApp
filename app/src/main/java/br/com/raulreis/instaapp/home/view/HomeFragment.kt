package br.com.raulreis.instaapp.home.view

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.BaseFragment
import br.com.raulreis.instaapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, Home.Presenter>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
) {

    override lateinit var presenter: Home.Presenter

    override fun setupViews() {
        binding?.rvHome?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvHome?.adapter = PostAdapter()
    }

    override fun setupPresenter() {

    }

    override fun getMenu(): Int? {
        return R.menu.menu_home
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            return PostViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return 30
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            holder.bind(R.drawable.ic_insta_add)
        }

        private class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(image: Int) {
                itemView.findViewById<ImageView>(R.id.imgHomePost).setImageResource(image)
            }
        }
    }
}