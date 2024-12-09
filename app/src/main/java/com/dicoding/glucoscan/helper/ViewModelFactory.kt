package com.dicoding.glucoscan.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.glucoscan.data.di.Injection
import com.dicoding.glucoscan.ui.screen.history.HistoryViewModel
import com.dicoding.glucoscan.ui.screen.home.HomeViewModel
import com.dicoding.glucoscan.ui.screen.login.SignInViewModel
import com.dicoding.glucoscan.ui.screen.login.SignUpViewModel
import com.dicoding.glucoscan.ui.screen.scan.ScanViewModel
import com.dicoding.glucoscan.ui.screen.setting.SettingViewModel
import com.dicoding.glucoscan.ui.screen.usereditor.PasswordEditorViewModel
import com.dicoding.glucoscan.ui.screen.usereditor.ProfileEditorViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(mApplication, Injection.provideRepository(mApplication, "register")) as T
        } else if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(mApplication, Injection.provideRepository(mApplication, "login")) as T
        } else if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            return ScanViewModel(mApplication, Injection.provideRepository(mApplication, "scan")) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mApplication, Injection.provideRepository(mApplication, "home")) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(mApplication, Injection.provideRepository(mApplication, "logout")) as T
        } else if (modelClass.isAssignableFrom(PasswordEditorViewModel::class.java)) {
            return PasswordEditorViewModel(mApplication, Injection.provideRepository(mApplication, "changePassword")) as T
        } else if (modelClass.isAssignableFrom(ProfileEditorViewModel::class.java)) {
            return ProfileEditorViewModel(mApplication, Injection.provideRepository(mApplication, "changeData")) as T
        } else if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}