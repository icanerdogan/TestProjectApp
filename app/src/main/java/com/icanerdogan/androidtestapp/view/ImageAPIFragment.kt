package com.icanerdogan.androidtestapp.view

import android.os.Bundle
import android.provider.VoicemailContract
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.icanerdogan.androidtestapp.R
import com.icanerdogan.androidtestapp.adapter.ImageRecyclerAdapter
import com.icanerdogan.androidtestapp.databinding.FragmentImageApiBinding
import com.icanerdogan.androidtestapp.util.Status
import com.icanerdogan.androidtestapp.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageAPIFragment @Inject constructor(
    val imageRecyclerAdapter: ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel : ArtViewModel
    private var fragmentBinding : FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        // Aramaya bişey yazdığında kitlenmemesi için delay koyucaz!
        var job : Job? = null
        binding.imageApiSearchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }
        subscribeToObservers()

        binding.imageApiRecyclerView.adapter = imageRecyclerAdapter
        binding.imageApiRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->  imageResult.previewURL }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    fragmentBinding?.imageApiProgressBar?.visibility = View.GONE

                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                    fragmentBinding?.imageApiProgressBar?.visibility = View.GONE

                }

                Status.LOADING -> {
                    fragmentBinding?.imageApiProgressBar?.visibility = View.VISIBLE

                }
            }

        })
    }

}