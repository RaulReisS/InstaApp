package br.com.raulreis.instaapp.profile.view

import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.BaseFragment
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.UserAuth
import br.com.raulreis.instaapp.databinding.FragmentProfileBinding
import br.com.raulreis.instaapp.profile.Profile
import br.com.raulreis.instaapp.profile.presentation.ProfilePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

    override lateinit var presenter: Profile.Presenter

    private val adapter = PostAdapter()
    private var uuid : String? = null

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun setupViews() {

        uuid = arguments?.getString(KEY_USER_ID)

        binding?.rvProfile?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.rvProfile?.adapter = adapter
        binding?.navProfileTabs?.setOnNavigationItemSelectedListener(this)

        presenter.fetchUserProfile(uuid)
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

        presenter.fetchUserPosts(uuid)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_profile_grid -> {
                binding?.rvProfile?.layoutManager = GridLayoutManager(requireContext(), 3)
            }
            R.id.menu_profile_list -> {
                binding?.rvProfile?.layoutManager = LinearLayoutManager(requireContext())
            }
        }
        return true
    }

    companion object {
        const val KEY_USER_ID = "key_user_id"
    }
}