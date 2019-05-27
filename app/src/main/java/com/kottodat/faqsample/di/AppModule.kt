package com.kottodat.faqsample.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.kottodat.faqsample.scene.login.data.LoginDataSource
import com.kottodat.faqsample.scene.login.data.LoginRepository
import com.kottodat.faqsample.util.rx.AppSchedulerProvider
import com.kottodat.faqsample.util.rx.SchedulerProvider

@Module
internal object AppModule {
    @Singleton
    @JvmStatic
    @Provides
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Singleton
    @Provides
    @JvmStatic
    fun provideLoginRepository(loginDataSource: LoginDataSource): LoginRepository = LoginRepository(loginDataSource)

    @Singleton
    @Provides
    @JvmStatic
    fun provideLoginDataSource(firebaseAuth: FirebaseAuth): LoginDataSource = LoginDataSource(firebaseAuth)

    @Singleton
    @Provides
    @JvmStatic
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}