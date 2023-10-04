package br.com.raulreis.instaapp.register.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.util.TxtWatcher
import br.com.raulreis.instaapp.databinding.FragmentRegisterNamePasswordBinding
import br.com.raulreis.instaapp.register.RegisterNameAndPassword
import br.com.raulreis.instaapp.register.presentation.RegisterNameAndPasswordPresenter

class RegisterNamePasswordFragment :
    Fragment(R.layout.fragment_register_name_password),
    RegisterNameAndPassword.View {


    private var binding : FragmentRegisterNamePasswordBinding? = null

    override lateinit var presenter: RegisterNameAndPassword.Presenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterNamePasswordBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepository()
        presenter = RegisterNameAndPasswordPresenter(this, repository)

        val email = arguments?.getString(KEY_EMAIL) ?: throw IllegalArgumentException("email not found")

        binding?.let {
            with(it) {

                txvRegisterLogin.setOnClickListener {
                    activity?.finish()
                }

                btnRegisterNameNext.setOnClickListener {
                    presenter.create(
                        email,
                        edtRegisterName.text.toString(),
                        edtRegisterPassword.text.toString(),
                        edtRegisterConfirm.text.toString()
                    )
                }

                edtRegisterName.addTextChangedListener(watcher)
                edtRegisterPassword.addTextChangedListener(watcher)
                edtRegisterConfirm.addTextChangedListener(watcher)

                edtRegisterName.addTextChangedListener(TxtWatcher {
                    displayNameFailure(null)
                })
                edtRegisterPassword.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)
                })
                edtRegisterConfirm.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)
                })


            }
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.btnRegisterNameNext?.showProgress(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.edtRegisterNameInput?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.edtRegisterPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateSuccess(name: String) {
        // TODO: Abrir a tela de bem-vindo
    }

    private val watcher = TxtWatcher {
        binding?.btnRegisterNameNext?.isEnabled = binding?.edtRegisterName?.text.toString().isNotEmpty()
                && binding?.edtRegisterPassword?.text.toString().isNotEmpty()
                && binding?.edtRegisterConfirm?.text.toString().isNotEmpty()
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        const val KEY_EMAIL = "key_email"
    }
}