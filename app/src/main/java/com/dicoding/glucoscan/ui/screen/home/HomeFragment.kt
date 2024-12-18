package com.dicoding.glucoscan.ui.screen.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.data.response.UserData
import com.dicoding.glucoscan.databinding.FragmentHomeBinding
import com.dicoding.glucoscan.helper.DailyRoundedAdapter
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.helper.createTimestamp
import com.dicoding.glucoscan.helper.get7DateBehind
import com.dicoding.glucoscan.ui.screen.scan.CameraActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel =
            ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application))[HomeViewModel::class.java]
        setupView()
        setupAction()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupView(){
        arguments?.getParcelable<UserData>("user").let{
            Glide.with(requireContext())
                .load(it?.profilePicture)
                .into(binding.ivProfile)
            binding.tvUsername.text = "Halo, ${it?.name}"
        }

        val date = createTimestamp("date")
        val lastDate = get7DateBehind().lastIndex
        val month = createTimestamp("monthName")
        binding.titleDate.text = "$lastDate-$date $month"

        binding.recyclerDailyRounded.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDailyRounded.adapter = DailyRoundedAdapter(homeViewModel.getDailyRoundedData().take(7))
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupAction(){
        binding.overlayView.setOnTouchListener { view, motionEvent -> true }

        binding.llRekomendasiCard.setOnClickListener {
            binding.overlayView.visibility = View.VISIBLE
            binding.popUpInfoCard.visibility = View.VISIBLE
        }

        binding.btnClose.setOnClickListener {
            binding.overlayView.visibility = View.GONE
            binding.popUpInfoCard.visibility = View.GONE
        }

        binding.ivScan.setOnClickListener {
            val intent = Intent(requireContext(), CameraActivity::class.java)
            startActivity(intent)
        }
    }

}