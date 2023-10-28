package br.com.raulreis.instaapp.home.view


import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.BaseFragment
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.databinding.FragmentHomeBinding
import br.com.raulreis.instaapp.home.Home
import br.com.raulreis.instaapp.home.presentation.HomePresenter

class HomeFragment() : BaseFragment<FragmentHomeBinding, Home.Presenter>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {

    override lateinit var presenter: Home.Presenter

    private val adapter = FeedAdapter()

    override fun setupViews() {
        binding?.rvHome?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvHome?.adapter = adapter

        presenter.fetchFeed()
    }

    override fun setupPresenter() {
        presenter = HomePresenter(this, DependencyInjector.homeRepository())
    }

    override fun getMenu(): Int? {
        return R.menu.menu_profile
    }

    override fun showProgress(enabled: Boolean) {
        binding?.progressHome?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun displayEmptyPosts() {
        binding?.txvHome?.visibility = View.VISIBLE
        binding?.rvHome?.visibility = View.GONE
    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.txvHome?.visibility = View.GONE
        binding?.rvHome?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
    }
}