package com.kottodat.faqsample.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.kottodat.faqsample.di.ViewModelKey
import com.kottodat.faqsample.scene.login.ui.login.LoginActivity
import com.kottodat.faqsample.scene.login.ui.login.LoginViewModel
import com.kottodat.faqsample.scene.setting.SettingsActivity
import com.kottodat.faqsample.scene.setting.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SettingActivityModule {
    @Binds
    fun providesAppCompatActivity(shopActivity: SettingsActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel
}