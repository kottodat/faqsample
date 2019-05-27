package com.kottodat.faqsample.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.kottodat.faqsample.di.ViewModelKey
import com.kottodat.faqsample.scene.login.ui.login.LoginActivity
import com.kottodat.faqsample.scene.login.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoginActivityModule {
    @Binds
    fun providesAppCompatActivity(shopActivity: LoginActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

}