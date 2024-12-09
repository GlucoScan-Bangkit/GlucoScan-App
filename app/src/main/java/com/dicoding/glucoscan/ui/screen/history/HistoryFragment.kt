package com.dicoding.glucoscan.ui.screen.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.FragmentHistoryBinding
import com.dicoding.glucoscan.helper.HistoryAdapter
import com.dicoding.glucoscan.helper.RiwayatRoundedAdapter
import com.dicoding.glucoscan.helper.timeStamp

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)

        setupView()
        setupAction()
        return binding.root
    }

    private fun setupView(){
        binding.titleMonth.text = timeStamp("monthName")

        binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRiwayat.adapter = RiwayatRoundedAdapter(viewModel.getDate().take(7))

        binding.rvRiwayatActivity.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRiwayatActivity.adapter = HistoryAdapter(viewModel.getDate().take(7))
    }

    private fun setupAction(){
        binding.overlayView.setOnTouchListener { view, motionEvent ->
            binding.overlayView.visibility = View.GONE
            binding.popUpInfoCard.visibility = View.GONE
            true
        }

        binding.informationCard.setOnClickListener {
            binding.overlayView.visibility = View.VISIBLE
            binding.popUpInfoCard.visibility = View.VISIBLE
        }
    }
}