package br.com.raulreis.instaapp.register.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.util.TxtWatcher
import br.com.raulreis.instaapp.databinding.FragmentRegisterEmailBinding
import br.com.raulreis.instaapp.register.RegisterEmail
import br.com.raulreis.instaapp.register.presentation.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email), RegisterEmail.View {

    private var binding : FragmentRegisterEmailBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterEmailBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepository()
        presenter = RegisterEmailPresenter(this, repository)

        binding?.let {
            with(it) {

                when(resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        imgRegisterPlaceholder.imageTintList = ColorStateList.valueOf(Color.WHITE)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                    }
                }

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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun onDestroy() {
        binding = null
        fragmentAttachListener = null
        presenter.onDestroy()
        super.onDestroy()
    }

    private val watcher = TxtWatcher {
        binding?.btnRegisterEmailNext?.isEnabled = binding?.edtRegisterEmail?.text.toString().isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.btnRegisterEmailNext?.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.edtRegisterEmailInput?.error = emailError?.let { getString(it) }
    }
    override fun onEmailFailure(message: String) {
        binding?.edtRegisterEmailInput?.error = message
    }

    override fun goToNameAndPasswordScreen(email: String) {
        fragmentAttachListener?.goToNameAndPasswordScreen(email)
    }
}