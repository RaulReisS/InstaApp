package br.com.raulreis.instaapp.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.databinding.FragmentImageCropperBinding
import java.io.File

class CropperImageFragment : Fragment(R.layout.fragment_image_cropper) {

    private var binding : FragmentImageCropperBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentImageCropperBinding.bind(view)

        val uri = arguments?.getParcelable<Uri>(KEY_URI)

        binding?.let {
            with(it) {
                with(containerCropper) {
                    setAspectRatio(1, 1)
                    setFixedAspectRatio(true)
                    setImageUriAsync(uri)

                    setOnCropImageCompleteListener { view, result ->
                        setFragmentResult("cropKey", bundleOf(KEY_URI to result.uri))

                        parentFragmentManager.popBackStack()
                    }
                }

                btnCropperCancel.setOnClickListener {
                    parentFragmentManager.popBackStack()
                }

                btnCropperSave.setOnClickListener {
                    val dir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    if(dir != null){
                        val uriToSaved = Uri.fromFile(File(dir.path, System.currentTimeMillis().toString()+".jpeg"))
                        containerCropper.saveCroppedImageAsync(uriToSaved)
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        const val KEY_URI = "key_uri"
    }

}