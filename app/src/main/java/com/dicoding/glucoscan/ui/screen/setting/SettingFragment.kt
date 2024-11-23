package com.dicoding.glucoscan.ui.screen.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val settingViewModel =
            ViewModelProvider(this)[SettingViewModel::class.java]

        binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.boxEditProfile.icEdit.setImageResource(R.drawable.ic_profile_edit)
        binding.boxEditProfile.content.text = "Perbarui profil"

        binding.boxEditPassword.icEdit.setImageResource(R.drawable.ic_change_password)
        binding.boxEditPassword.content.text = "Ubah kata sandi"


        return binding.root
    }

}