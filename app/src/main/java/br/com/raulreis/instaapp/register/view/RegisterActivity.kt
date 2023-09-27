package br.com.raulreis.instaapp.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.databinding.ActivityRegisterBinding
import br.com.raulreis.instaapp.register.view.RegisterNamePasswordFragment.Companion.KEY_EMAIL

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val fragment = RegisterEmailFragment()
        replaceFragment(fragment)
        }

    override fun goToNameAndPasswordScreen(email: String) {
        val args = Bundle()
        args.putString(KEY_EMAIL, email)
        val fragment = RegisterNamePasswordFragment()
        fragment.arguments = args

        /*
        *
        * val fragment = RegisterNamePasswordFragment().apply {
        *   arguments = Bundle().apply {
        *       putString(KEY_EMAIL, email)
        *   }
        * }
        *
        * */

        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {

        if (supportFragmentManager.findFragmentById(R.id.fragmentRegister) == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentRegister, fragment)
                commit()
            }
        }
        else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentRegister, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }
}