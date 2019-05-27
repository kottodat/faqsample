package com.kottodat.faqsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kottodat.faqsample.scene.login.ui.login.LoginActivity
import org.jetbrains.anko.startActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        startActivity<LoginActivity>()
    }
}
