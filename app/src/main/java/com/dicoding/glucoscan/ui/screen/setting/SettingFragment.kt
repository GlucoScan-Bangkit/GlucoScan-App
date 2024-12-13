package com.dicoding.glucoscan.ui.screen.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.glucoscan.R
import com.dicoding.glucoscan.data.EncryptedSharedPreference.deleteToken
import com.dicoding.glucoscan.data.EncryptedSharedPreference.getToken
import com.dicoding.glucoscan.data.response.UserData
import com.dicoding.glucoscan.databinding.FragmentSettingBinding
import com.dicoding.glucoscan.helper.ViewModelFactory
import com.dicoding.glucoscan.ui.screen.login.SignInActivity
import com.dicoding.glucoscan.ui.screen.usereditor.PasswordEditorActivity
import com.dicoding.glucoscan.ui.screen.usereditor.ProfileEditorActivity
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        settingViewModel =
            ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application))[SettingViewModel::class.java]

        binding = FragmentSettingBinding.inflate(inflater, container, false)

        val bundle = Bundle()

        arguments?.getParcelable<UserData>("user")?.let {
            Glide.with(this)
                .load(it.profilePicture)
                .into(binding.ivProfile)
            binding.tvUsername.text = it.name
            binding.tvEmail.text = it.email
            bundle.putParcelable("user", it)
        }

        binding.boxEditProfile.icEdit.setImageResource(R.drawable.ic_profile_edit)
        binding.boxEditProfile.content.text = "Perbarui profil"
        binding.boxEditProfile.root.setOnClickListener {
            val intent = Intent(requireContext(), ProfileEditorActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.boxEditPassword.icEdit.setImageResource(R.drawable.ic_change_password)
        binding.boxEditPassword.content.text = "Ubah kata sandi"
        binding.boxEditPassword.root.setOnClickListener {
            val intent = Intent(requireContext(), PasswordEditorActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        binding.btnCancelPopup.setOnClickListener {
            binding.popUpInfoCard.visibility = View.GONE
        }

        binding.btnLogout.setOnClickListener{
            binding.popUpInfoCard.visibility = View.VISIBLE
        }

        binding.btnPopupLogout.setOnClickListener{
            auth.signOut()
            settingViewModel.logout(getToken(requireContext())!!)
            deleteToken(requireContext())
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        return binding.root
    }

}