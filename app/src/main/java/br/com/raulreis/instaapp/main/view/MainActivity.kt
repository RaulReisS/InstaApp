package br.com.raulreis.instaapp.main.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.post.view.AddFragment
import br.com.raulreis.instaapp.common.extension.replaceFragment
import br.com.raulreis.instaapp.databinding.ActivityMainBinding
import br.com.raulreis.instaapp.home.view.HomeFragment
import br.com.raulreis.instaapp.main.LogoutListener
import br.com.raulreis.instaapp.profile.view.ProfileFragment
import br.com.raulreis.instaapp.search.view.SearchFragment
import br.com.raulreis.instaapp.splash.view.SplashActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity :
    AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    AddFragment.AddListener,
    SearchFragment.SearchListener,
    LogoutListener {

    private lateinit var binding : ActivityMainBinding

    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: Fragment
    private lateinit var addFragment: Fragment
    private lateinit var profileFragment: ProfileFragment

    private var currentFragment: Fragment? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }

        setSupportActionBar(binding.toolbarMain)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        addFragment = AddFragment()
        profileFragment = ProfileFragment()

        binding.bottomnavMain.setOnNavigationItemSelectedListener(this)
        binding.bottomnavMain.selectedItemId = R.id.menu_bottom_home
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var scrollToolbarEnabled = false

        when(item.itemId) {
            R.id.menu_bottom_home -> {
                if (currentFragment == homeFragment) return false
                currentFragment = homeFragment
            }
            R.id.menu_bottom_search-> {
                if (currentFragment == searchFragment) return false
                currentFragment = searchFragment
                scrollToolbarEnabled = false
            }
            R.id.menu_bottom_add -> {
                if (currentFragment == addFragment) return false
                currentFragment = addFragment
                scrollToolbarEnabled = false
            }
            R.id.menu_bottom_profile -> {
                if (currentFragment == profileFragment) return false
                currentFragment = profileFragment
                scrollToolbarEnabled = true
            }
        }

        setScrollToolbarEnaled(scrollToolbarEnabled)

        currentFragment?.let {
            replaceFragment(R.id.fragmentMain, it)
        }

        return true
    }

    private fun setScrollToolbarEnaled(enabled: Boolean) {
        val params = binding.toolbarMain.layoutParams as AppBarLayout.LayoutParams
        val coordinatParams = binding.appbarMain.layoutParams as CoordinatorLayout.LayoutParams

        if (enabled) {
            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatParams.behavior = AppBarLayout.Behavior()
            binding.appbarMain.layoutParams = coordinatParams
        } else {
            params.scrollFlags = 0
            coordinatParams.behavior = null
            binding.appbarMain.layoutParams = coordinatParams
        }
    }

    override fun onPostCreated() {
        homeFragment.presenter.clear()
        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null)
            profileFragment.presenter.clear()

        binding.bottomnavMain.selectedItemId = R.id.menu_bottom_home
    }

    override fun goToProfile(uuid: String) {
        val fragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ProfileFragment.KEY_USER_ID, uuid)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentMain, fragment, fragment.javaClass.simpleName + "detail")
            addToBackStack(null)
            commit()
        }
    }

    override fun logout() {

        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null)
            profileFragment.presenter.clear()

        homeFragment.presenter.clear()
        homeFragment.presenter.logout()

        val intent = Intent(baseContext, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}