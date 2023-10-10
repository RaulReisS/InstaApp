package br.com.raulreis.instaapp.splash.data

interface SplashDataSource {
    fun session(callback: SplashCallback)
}