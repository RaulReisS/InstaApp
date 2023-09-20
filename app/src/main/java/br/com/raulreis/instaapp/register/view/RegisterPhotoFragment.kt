package br.com.raulreis.instaapp.register.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.view.CustomDialog
import br.com.raulreis.instaapp.databinding.FragmentRegisterPhotoBinding

class RegisterPhotoFragment : Fragment(R.layout.fragment_register_photo) {

    private var binding: FragmentRegisterPhotoBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterPhotoBinding.bind(view)

        val customDialog = CustomDialog(requireContext())

        customDialog.addButton(R.string.photo, R.string.gallery) {
            when(it.id) {
                R.string.photo -> {

                }
                R.string.gallery -> {

                }
            }
        }

        customDialog.show()
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}