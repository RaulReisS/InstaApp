package br.com.raulreis.instaapp.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import br.com.raulreis.instaapp.common.util.TxtWatcher
import br.com.raulreis.instaapp.databinding.ActivityLoginBinding
import br.com.raulreis.instaapp.login.Login
import br.com.raulreis.instaapp.login.presentation.LoginPresenter
import br.com.raulreis.instaapp.main.view.MainActivity

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var binding: ActivityLoginBinding

    override lateinit var presenter: Login.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        presenter = LoginPresenter(this)

        with(binding) {
            edtLoginEmail.addTextChangedListener(watcher)
            edtLoginEmail.addTextChangedListener(TxtWatcher {
                displayEmailFailure(null)
            })
            edtLoginPassword.addTextChangedListener(watcher)
            edtLoginPassword.addTextChangedListener(TxtWatcher {
                displayPasswordFailure(null)
            })

            btnLoginEnter.setOnClickListener {
                presenter.login(edtLoginEmail.text.toString(), edtLoginPassword.text.toString())
            }
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private val watcher = TxtWatcher {
        binding.btnLoginEnter.isEnabled = binding.edtLoginEmail.text.toString().isNotEmpty()
                && binding.edtLoginPassword.text.toString().isNotEmpty()
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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onUserUnauthorized(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}