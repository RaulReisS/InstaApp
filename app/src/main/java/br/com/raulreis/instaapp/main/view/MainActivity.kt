package br.com.raulreis.instaapp.main.view

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
import br.com.raulreis.instaapp.camera.view.CameraFragment
import br.com.raulreis.instaapp.common.extension.replaceFragment
import br.com.raulreis.instaapp.databinding.ActivityMainBinding
import br.com.raulreis.instaapp.home.view.HomeFragment
import br.com.raulreis.instaapp.profile.view.ProfileFragment
import br.com.raulreis.instaapp.search.view.SearchFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainBinding

    private lateinit var homeFragment: Fragment
    private lateinit var searchFragment: Fragment
    private lateinit var cameraFragment: Fragment
    private lateinit var profileFragment: Fragment

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
        cameraFragment = CameraFragment()
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
            }
            R.id.menu_bottom_add -> {
                if (currentFragment == cameraFragment) return false
                currentFragment = cameraFragment
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

}