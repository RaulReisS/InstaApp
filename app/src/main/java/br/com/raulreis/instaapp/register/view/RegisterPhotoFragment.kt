package br.com.raulreis.instaapp.register.view

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.view.CropperImageFragment
import br.com.raulreis.instaapp.common.view.CustomDialog
import br.com.raulreis.instaapp.databinding.FragmentRegisterPhotoBinding
import br.com.raulreis.instaapp.register.RegisterPhoto
import br.com.raulreis.instaapp.register.presentation.RegisterPhotoPresenter

class RegisterPhotoFragment : Fragment(R.layout.fragment_register_photo), RegisterPhoto.View {

    private var binding: FragmentRegisterPhotoBinding? = null
    private var fragmentAttachListener : FragmentAttachListener? = null
    override lateinit var presenter : RegisterPhoto.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("cropKey") { requestKey, bundle ->  
            val uri = bundle.getParcelable<Uri>(CropperImageFragment.KEY_URI)
            onCropImageresult(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterPhotoBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepository()
        presenter = RegisterPhotoPresenter(this, repository)

        binding?.let {
            with(it) {
                btnRegisterSkip.setOnClickListener {
                    fragmentAttachListener?.goToMainScreen()
                }

                btnRegisterPhotoNext.isEnabled= true
                btnRegisterPhotoNext.setOnClickListener {
                    openDialog()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.btnRegisterPhotoNext?.showProgress(enabled)
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSuccess() {
        fragmentAttachListener?.goToMainScreen()
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun openDialog() {
        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.photo, R.string.gallery) {
            when(it.id) {
                R.string.photo -> {
                    fragmentAttachListener?.goToCameraScreen()
                }
                R.string.gallery -> {
                    fragmentAttachListener?.goToGallery()
                }
            }
        }
        customDialog.show()
    }

    private fun onCropImageresult(uri: Uri?) {
        if( uri != null) {
            val bitmap = if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)

            }
            else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
            binding?.imgRegisterProfile?.setImageBitmap(bitmap)

            presenter.updateUser(uri)
        }
    }

}