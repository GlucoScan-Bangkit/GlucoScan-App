package com.dicoding.glucoscan.ui.screen.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.FragmentHomeBinding
import com.dicoding.glucoscan.helper.DailyRoundedAdapter
import com.dicoding.glucoscan.ui.screen.scan.CameraActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

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