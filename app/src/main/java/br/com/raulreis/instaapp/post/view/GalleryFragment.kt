package br.com.raulreis.instaapp.post.view

import android.net.Uri
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.BaseFragment
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.databinding.FragmentGalleryBinding
import br.com.raulreis.instaapp.post.Post
import br.com.raulreis.instaapp.post.presentation.PostPresenter
import java.io.File

class GalleryFragment : BaseFragment<FragmentGalleryBinding, Post.Presenter> (
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
        ), Post.View {
    override lateinit var presenter: Post.Presenter

    private val adapter = PictureAdapter() {uri ->
        binding?.imgGallerySelected?.setImageURI(uri)
        binding?.nestedGallery?.smoothScrollTo(0,0)
        presenter.selectUri(uri)
    }

    override fun setupPresenter() {
        presenter = PostPresenter(this, DependencyInjector.postRepository(requireContext()))
    }

    override fun getMenu(): Int? {
        return R.menu.menu_send
    }

    override fun setupViews() {
        binding?.rvGallery?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.rvGallery?.adapter = adapter

       presenter.fetchPictures()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.progressGallery?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullPictures(pictures: List<Uri>) {
        binding?.txvGalleryEmpty?.visibility = View.GONE
        binding?.rvGallery?.visibility = View.VISIBLE
        adapter.items = pictures
        adapter.notifyDataSetChanged()
        binding?.imgGallerySelected?.setImageURI(pictures.first())
        binding?.nestedGallery?.smoothScrollTo(0,0)
        presenter.selectUri(pictures.first())
    }

    override fun displayEmptyPictures() {
        binding?.txvGalleryEmpty?.visibility = View.VISIBLE
        binding?.rvGallery?.visibility = View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_send -> {
                setFragmentResult("takePhotoKey", bundleOf("uri" to presenter.getSelectedUri()))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}