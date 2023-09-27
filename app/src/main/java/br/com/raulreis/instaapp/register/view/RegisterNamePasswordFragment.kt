package br.com.raulreis.instaapp.register.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.databinding.FragmentRegisterNamePasswordBinding

class RegisterNamePasswordFragment : Fragment(R.layout.fragment_register_name_password) {


    private var binding : FragmentRegisterNamePasswordBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterNamePasswordBinding.bind(view)

        val email = arguments?.getString(KEY_EMAIL)


    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        const val KEY_EMAIL = "key_email"
    }
}