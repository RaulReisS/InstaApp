package br.com.raulreis.instaapp.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import br.com.raulreis.instaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            edtLoginEmail.addTextChangedListener(watcher)
            edtLoginPassword.addTextChangedListener(watcher)

            btnLoginEnter.setOnClickListener {
                btnLoginEnter.showProgress(true)

                edtLoginEmailInput.error = "Esse e-mail é inválido"
                edtLoginPasswordInput.error = "Senha incorreta"

                Handler(Looper.getMainLooper()).postDelayed({
                    btnLoginEnter.showProgress(false)
                }, 2000)
            }
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.btnLoginEnter.isEnabled = s.toString().isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
}