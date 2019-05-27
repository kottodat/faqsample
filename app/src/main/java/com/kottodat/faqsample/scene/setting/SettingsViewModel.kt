package com.kottodat.faqsample.scene.setting

import androidx.lifecycle.ViewModel
import com.kottodat.faqsample.util.rx.SchedulerProvider
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

}