package br.com.raulreis.instaapp.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.databinding.FragmentRegisterWelcomeBinding

class RegisterWelcomeFragment : Fragment(R.layout.fragment_register_welcome) {

    private var binding : FragmentRegisterWelcomeBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterWelcomeBinding.bind(view)

        val name = arguments?.getString(KEY_NAME) ?: throw IllegalArgumentException("name not found")

        binding?.let {
            with(it) {
                txvWelcome.text = getString(R.string.welcome_to_instaapp, name)
                btnRegisterWelcomeNext.isEnabled = true
                btnRegisterWelcomeNext.setOnClickListener {
                    fragmentAttachListener?.goToPhotoScreen()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        const val KEY_NAME = "key_name"
    }
}