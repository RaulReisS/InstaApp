package br.com.raulreis.instaapp.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val fragment = RegisterEmailFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentRegister, fragment)
            commit()
        }
    }
}