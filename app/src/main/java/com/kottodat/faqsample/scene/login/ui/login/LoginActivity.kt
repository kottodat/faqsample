package com.kottodat.faqsample.scene.login.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kottodat.faqsample.R
import com.kottodat.faqsample.data.mapper.toResult
import com.kottodat.faqsample.di.ViewModelFactory
import com.kottodat.faqsample.scene.login.data.Result
import com.kottodat.faqsample.scene.login.data.model.LoggedInUser
import com.kottodat.gms.auth.RxGoogleSignIn
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject


class LoginActivity : DaggerAppCompatActivity() {

    private val RC_SIGN_IN: Int = 1000
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    private val googleSignIn: RxGoogleSignIn by lazy {
        RxGoogleSignIn(this, googleSignInOptions)
    }

    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

//        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
//            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer { result ->
            when (result) {
                is Result.Failure -> {
                    showLoginFailed(R.string.login_failed)
                    loading.visibility = View.GONE
                    Timber.d(result.e)
                }
                is Result.Success -> {
                    updateUiWithUser(result.data)
                    setResult(Activity.RESULT_OK)
                    //Complete and destroy login activity once successful
                    finish()
                }
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }

            login_google.setOnClickListener {
                // loading.visibility = View.VISIBLE
                googleSignIn.getSignInIntent().let {
                    startActivityForResult(it, RC_SIGN_IN)
                }
            }
        }
        logout()

    }

    private fun logout()
    {
        googleSignIn.signOut()
        loginViewModel.logout()
    }

    private fun updateUiWithUser(model: LoggedInUser) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            if (data != null) {
                googleSignIn.getIdToken(data).let {
                    loading.visibility = View.VISIBLE
                    loginViewModel.googleLogin(it)
                }
            } else {
//                Log.w("Login", "signInWithCredential:failure", task.exception)
                Toast.makeText(this, "Auth Failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
