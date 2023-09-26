package br.com.raulreis.instaapp.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.util.TxtWatcher
import br.com.raulreis.instaapp.databinding.ActivityLoginBinding
import br.com.raulreis.instaapp.login.Login
import br.com.raulreis.instaapp.login.presentation.LoginPresenter
import br.com.raulreis.instaapp.main.view.MainActivity
import br.com.raulreis.instaapp.register.view.RegisterActivity

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var binding: ActivityLoginBinding

    override lateinit var presenter: Login.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        presenter = LoginPresenter(this, DependencyInjector.loginRepository())

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
            txvLoginRegister.setOnClickListener {
                goToRegisterActivity()
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

    private fun goToRegisterActivity() {
       startActivity(Intent(this, RegisterActivity::class.java))
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
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onUserUnauthorized(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}