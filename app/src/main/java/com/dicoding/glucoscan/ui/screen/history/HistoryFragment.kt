package com.dicoding.glucoscan.ui.screen.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.glucoscan.data.Result
import com.dicoding.glucoscan.databinding.FragmentHistoryBinding
import com.dicoding.glucoscan.helper.HistoryAdapter
import com.dicoding.glucoscan.helper.RiwayatRoundedAdapter
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.helper.changeFormatTimestamp
import com.dicoding.glucoscan.helper.createTimestamp

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private val monthYear = createTimestamp("year") + "-" + createTimestamp("month")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application))[HistoryViewModel::class.java]

        setupView()
        setupAction()
        return binding.root
    }

    private fun setupView(){
        binding.titleMonth.text = createTimestamp("monthName")

        val date = viewModel.getDate().take(7)

        binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRiwayat.adapter = RiwayatRoundedAdapter(date){ selectedItem ->
            val fullDate = "$monthYear-$selectedItem"
            viewModel.getHistory(fullDate)
            val result = changeFormatTimestamp(fullDate, "EEE")
            binding.tvDaily.text = result
        }

        binding.rvRiwayatActivity.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewModel.getHistory("$monthYear-${date.first()}")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupAction(){
        viewModel.history.observe(viewLifecycleOwner){ result ->
            when(result){
                is Result.Success -> {
                    binding.rvRiwayatActivity.adapter = HistoryAdapter(result.data.data)
                    binding.tvDailyScan.text = result.data.data?.size.toString()
                }
                is Result.Error -> {
                    //
                }
                Result.Loading -> {
                    //
                }
            }
        }

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