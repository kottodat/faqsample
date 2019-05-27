package com.kottodat.faqsample.di

import android.app.Application
import com.kottodat.faqsample.APP
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        AppModule::class]
)
interface AppComponent : AndroidInjector<APP> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(app: APP)
}