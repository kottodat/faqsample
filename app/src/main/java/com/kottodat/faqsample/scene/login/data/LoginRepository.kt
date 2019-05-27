package com.kottodat.faqsample.scene.login.data

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.kottodat.faqsample.scene.login.data.model.LoggedInUser
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository @Inject constructor(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): Single<LoggedInUser> {
        // handle login
        return dataSource.signInWithEmailAndPassword(username, password)
    }

    fun googleLogin(idToken: String): Single<LoggedInUser> {
        GoogleAuthProvider.getCredential(idToken, null).let {
            return dataSource.signInWithCredential(it)
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
