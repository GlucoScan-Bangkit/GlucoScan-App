package com.dicoding.glucoscan.ui.screen.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.databinding.FragmentSettingBinding
import com.dicoding.glucoscan.ui.screen.login.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var auth: FirebaseAuth

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

        auth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener{
            auth.signOut()
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        return binding.root
    }

}