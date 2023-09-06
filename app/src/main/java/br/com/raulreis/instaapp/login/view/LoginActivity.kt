package br.com.raulreis.instaapp.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import br.com.raulreis.instaapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtEmail = findViewById<TextInputEditText>(R.id.edtLoginEmail)
        val edtPassword = findViewById<TextInputEditText>(R.id.edtLoginPassword)

        val buttonEnter = findViewById<LoadingButton>(R.id.btnLoginEnter)

        edtEmail.addTextChangedListener(watcher)
        edtPassword.addTextChangedListener(watcher)

        buttonEnter.setOnClickListener {

            buttonEnter.showProgress(true)

            findViewById<TextInputLayout>(R.id.edtLoginEmailInput)
                .error = "Esse e-mail é inválido"
            findViewById<TextInputLayout>(R.id.edtLoginPasswordInput)
                .error = "Senha incorreta"

            Handler(Looper.getMainLooper()).postDelayed({
                buttonEnter.showProgress(false)
            }, 2000)

        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            findViewById<LoadingButton>(R.id.btnLoginEnter).isEnabled = s.toString().isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
}