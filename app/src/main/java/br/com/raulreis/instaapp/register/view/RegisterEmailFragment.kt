package br.com.raulreis.instaapp.register.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.util.TxtWatcher
import br.com.raulreis.instaapp.databinding.FragmentRegisterEmailBinding
import br.com.raulreis.instaapp.register.RegisterEmail

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email), RegisterEmail.View {
    private var binding : FragmentRegisterEmailBinding? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterEmailBinding.bind(view)

        binding?.let {
            with(it) {
                txvRegisterLogin.setOnClickListener {
                    activity?.finish()
                }

                btnRegisterEmailNext.setOnClickListener {
                    presenter.create(edtRegisterEmail.text.toString())
                }

                edtRegisterEmail.addTextChangedListener(watcher)
                edtRegisterEmail.addTextChangedListener(TxtWatcher {
                    displayEmailFailure(null)
                })
            }
        }
    }

    private val watcher = TxtWatcher {
        binding?.btnRegisterEmailNext?.isEnabled = binding?.edtRegisterEmail?.text.toString().isNotEmpty()
    }

    override fun displayEmailFailure(emailError: Int?) {
    }

    override fun onDestroy() {
        binding = null
        // presenter.onDestroy()
        super.onDestroy()
    }
}