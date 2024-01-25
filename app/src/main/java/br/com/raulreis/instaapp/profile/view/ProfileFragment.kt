package br.com.raulreis.instaapp.profile.view

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.BaseFragment
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.model.Post
import br.com.raulreis.instaapp.common.model.User
import br.com.raulreis.instaapp.databinding.FragmentProfileBinding
import br.com.raulreis.instaapp.main.LogoutListener
import br.com.raulreis.instaapp.profile.Profile
import br.com.raulreis.instaapp.profile.presentation.ProfilePresenter
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

    override lateinit var presenter: Profile.Presenter

    private val adapter = PostAdapter()
    private var uuid : String? = null

    private var logoutListener: LogoutListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LogoutListener)
            logoutListener = context
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun setupViews() {

        uuid = arguments?.getString(KEY_USER_ID)

        binding?.rvProfile?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.rvProfile?.adapter = adapter
        binding?.navProfileTabs?.setOnNavigationItemSelectedListener(this)

        binding?.btnEditProfile?.setOnClickListener {
            if (it.tag == true) {
                binding?.btnEditProfile?.text = getString(R.string.follow)
                binding?.btnEditProfile?.tag = false
                presenter.followUser(uuid, false)
            }
            else if (it.tag == false) {
                binding?.btnEditProfile?.text = getString(R.string.unfollow)
                binding?.btnEditProfile?.tag = true
                presenter.followUser(uuid, true)
            }

        }

        presenter.fetchUserProfile(uuid)
    }

    override fun showProgress(enabled: Boolean) {
        binding?.progressProfile?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(user: Pair<User, Boolean?>) {
        val (userAuth, following) = user

        binding?.txvProfilePostsCount?.text = userAuth.postCount.toString()
        binding?.txvProfileFollowingCount?.text = userAuth.following.toString()
        binding?.txvProfileFollowersCount?.text = userAuth.followers.toString()
        binding?.txvProfileUsername?.text = userAuth.name
        binding?.txvProfileBio?.text = "TODO"

        binding?.let {
            Glide.with(requireContext()).load(userAuth.photoUrl).into(it.imgProfileIcon)
        }

        binding?.btnEditProfile?.text = when(following) {
            null -> getString(R.string.edit_profile)
            true -> getString(R.string.unfollow)
            false -> getString(R.string.follow)
        }

        binding?.imgAddStory?.visibility = if (following == null) View.VISIBLE else View.GONE

        binding?.btnEditProfile?.tag = following

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuLogout -> {
                logoutListener?.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY_USER_ID = "key_user_id"
    }
}