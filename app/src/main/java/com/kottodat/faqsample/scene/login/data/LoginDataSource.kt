package com.kottodat.faqsample.scene.login.data

import android.text.TextUtils
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.kottodat.faqsample.scene.login.data.model.LoggedInUser
import com.kottodat.gsfirebase.auth.RxFirebaseAuth
import io.reactivex.Single
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource @Inject constructor(
    val firebaseAuth: FirebaseAuth
) {

    fun signInWithCredential(credential: AuthCredential): Single<LoggedInUser> {
        return RxFirebaseAuth.signInWithCredential(firebaseAuth, credential)
            .map { t ->
                LoggedInUser(t.providerId, if (TextUtils.isEmpty(t.displayName)) "user" else t.displayName!!)
            }
    }

    fun signInWithEmailAndPassword(username: String, password: String): Single<LoggedInUser> {
        return RxFirebaseAuth.signInWithEmailAndPassword(firebaseAuth, username, password)
            .map { t ->
                LoggedInUser(t.providerId, if (TextUtils.isEmpty(t.displayName)) "user" else t.displayName!!)
            }
    }

    fun logout() = RxFirebaseAuth.signOut(firebaseAuth)
}

