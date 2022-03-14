package com.icanerdogan.androidtestapp.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icanerdogan.androidtestapp.R
import com.icanerdogan.androidtestapp.databinding.FragmentArtDetailsBinding

class ArtDetailsFragment : Fragment(R.layout.fragment_art_details) {
    private var fragmentBinding: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        binding.artDetailImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageAPIFragment())
        }

        // Kullanıcı geri tuşuna bastığında ne olacak?
        val callback = object  : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                // Bi önceki stackte ne varsa oraya git ve kapat!
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}