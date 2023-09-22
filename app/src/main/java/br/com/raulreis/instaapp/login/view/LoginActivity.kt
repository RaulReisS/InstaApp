package br.com.raulreis.instaapp.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.raulreis.instaapp.common.util.TxtWatcher
import br.com.raulreis.instaapp.databinding.ActivityLoginBinding
import br.com.raulreis.instaapp.login.Login

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            edtLoginEmail.addTextChangedListener(watcher)
            edtLoginPassword.addTextChangedListener(watcher)

            btnLoginEnter.setOnClickListener {
                // Chamar o presenter
            }
        }
    }

    private val watcher = TxtWatcher {
        binding.btnLoginEnter.isEnabled = it.isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding.btnLoginEnter.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.edtLoginEmailInput.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.edtLoginPasswordInput.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticated() {
        // Ir para a tela principal
    }

    override fun onUserUnauthorized() {
        // Mostrar um alerta
    }
}