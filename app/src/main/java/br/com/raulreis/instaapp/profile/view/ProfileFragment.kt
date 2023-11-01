package br.com.raulreis.instaapp.profile.view

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.BaseFragment
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth
import br.com.raulreis.instaapp.databinding.FragmentProfileBinding
import br.com.raulreis.instaapp.profile.Profile
import br.com.raulreis.instaapp.profile.presentation.ProfilePresenter

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View {

    override lateinit var presenter: Profile.Presenter

    private val adapter = PostAdapter()

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun setupViews() {
        binding?.rvProfile?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.rvProfile?.adapter = adapter

        presenter.fetchUserProfile()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.progressProfile?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(userAuth: UserAuth) {
        binding?.txvProfilePostsCount?.text = userAuth.postCount.toString()
        binding?.txvProfileFollowingCount?.text = userAuth.followingCount.toString()
        binding?.txvProfileFollowersCount?.text = userAuth.followersCount.toString()
        binding?.txvProfileUsername?.text = userAuth.name
        binding?.txvProfileBio?.text = "TODO"
        binding?.imgProfileIcon?.setImageURI(userAuth.photoUri)

        presenter.fetchUserPosts()
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun displayEmptyPosts() {
        binding?.txvProfileEmpty?.visibility = View.VISIBLE
        binding?.rvProfile?.visibility = View.GONE

    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.txvProfileEmpty?.visibility = View.GONE
        binding?.rvProfile?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
    }

    override fun getMenu(): Int? {
        return R.menu.menu_profile
    }
}