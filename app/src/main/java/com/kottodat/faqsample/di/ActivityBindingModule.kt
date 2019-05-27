package com.kottodat.faqsample.di

import com.kottodat.faqsample.di.activitymodule.LoginActivityModule
import com.kottodat.faqsample.di.activitymodule.SettingActivityModule
import com.kottodat.faqsample.scene.login.ui.login.LoginActivity
import com.kottodat.faqsample.scene.setting.SettingsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule
{
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    fun loginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [SettingActivityModule::class])
    fun settingsActivity(): SettingsActivity
}