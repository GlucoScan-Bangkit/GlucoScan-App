package com.dicoding.glucoscan.ui.screen.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.databinding.FragmentHomeBinding
import com.dicoding.glucoscan.helper.DailyRoundedAdapter
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.ui.screen.scan.CameraActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeViewModel =
            ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application))[HomeViewModel::class.java]
        homeViewModel.getDashboard()

        homeViewModel.user.observe(viewLifecycleOwner){ result ->
            when (result){
                is Result.Success -> {
                    Glide.with(requireContext())
                        .load(result.data.user?.profilePicture)
                        .into(binding.ivProfile)
                    binding.tvUsername.text = result.data.user?.name
                }
                is Result.Loading -> {

                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recyclerDailyRounded.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDailyRounded.adapter = DailyRoundedAdapter(homeViewModel.getDailyRoundedData().take(7))
        binding.ivScan.setOnClickListener {
            val intent = Intent(requireContext(), CameraActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}