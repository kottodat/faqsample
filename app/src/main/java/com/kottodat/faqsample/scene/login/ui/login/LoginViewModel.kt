package com.kottodat.faqsample.scene.login.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kottodat.faqsample.R
import com.kottodat.faqsample.data.mapper.toResult
import com.kottodat.faqsample.scene.login.data.LoginRepository
import com.kottodat.faqsample.scene.login.data.Result
import com.kottodat.faqsample.scene.login.data.model.LoggedInUser
import com.kottodat.faqsample.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Result<LoggedInUser>>()
    val loginResult: LiveData<Result<LoggedInUser>> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password)
            .toResult(schedulerProvider)
            .subscribeBy(
                onNext = { it -> _loginResult.value = it },
                onError = { e -> Timber.e(e) }
            ).addTo(compositeDisposable)
    }
    fun googleLogin(idToken:String) {
        loginRepository.googleLogin(idToken)
            .toResult(schedulerProvider)
            .subscribeBy(
                onNext = { it -> _loginResult.value = it },
                onError = { e -> Timber.e(e) }
            ).addTo(compositeDisposable)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5;
    }

    fun logout() {
        loginRepository.logout()

    }

}
