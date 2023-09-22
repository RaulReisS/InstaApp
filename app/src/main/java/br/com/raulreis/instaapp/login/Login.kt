package br.com.raulreis.instaapp.login

interface Login {

    // Camada de view
    interface View {
        fun showProgress(enabled: Boolean)

        fun displayEmailFailure(emailError: Int?)
        fun displayPasswordFailure(passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized()
    }
}