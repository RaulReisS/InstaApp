package br.com.raulreis.instaapp.post.presentation

import android.net.Uri
import br.com.raulreis.instaapp.post.Post
import br.com.raulreis.instaapp.post.data.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PostPresenter(
    private var view: Post.View?,
    private val repository: PostRepository
    ) : Post.Presenter, CoroutineScope {

    private var uri: Uri? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    override fun fetchPictures() {
        // Aqui acontece a chamada na thread MAIN (UI)
        view?.showProgress(true)
        launch {
            // Aqui dentro da lauch, acontece a chamada paralela (corotina IO)
            val pictures = repository.fetchPictures()

            withContext(Dispatchers.Main) {
                // Aqui dentro executa de volta na Main Thread
                if (pictures.isEmpty())
                    view?.displayEmptyPictures()
                else
                    view?.displayFullPictures(pictures)
                view?.showProgress(false)
            }
        }
    }

    override fun selectUri(uri: Uri) {
        this.uri = uri
    }

    override fun getSelectedUri(): Uri? {
        return this.uri
    }

    override fun onDestroy() {
        job.cancel()
        view = null
    }
}