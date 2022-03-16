package com.icanerdogan.androidtestapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.icanerdogan.androidtestapp.R
import com.icanerdogan.androidtestapp.databinding.FragmentArtDetailsBinding
import com.icanerdogan.androidtestapp.util.Status
import com.icanerdogan.androidtestapp.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide : RequestManager
)  : Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding: FragmentArtDetailsBinding? = null
    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

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

        subscribeToObservers()

        binding.artDetailSaveButton.setOnClickListener {
            viewModel.makeArt(binding.artDetailArtNameEditText.text.toString(), binding.artDetailArtistNameEditText.text.toString(), binding.artDetailYearEditText.text.toString())
        }
    }

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let { binding ->
                glide.load(url).into(binding.artDetailImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message?: "Error", Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }
    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}