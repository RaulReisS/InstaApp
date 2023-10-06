package br.com.raulreis.instaapp.register.view

interface FragmentAttachListener {
    fun goToNameAndPasswordScreen(email: String)

    fun goToWelcomeScreen(name: String)

    fun goToPhotoScreen()

    fun goToMainScreen()

    fun goToGallery()

    fun goToCameraScreen()
}