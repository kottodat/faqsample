package com.kottodat.gms.auth

import android.accounts.Account
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kottodat.gsfirebase.rx.RxTask
import io.reactivex.Completable
import io.reactivex.Single


/**
 * GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
.requestIdToken( mActivity.getString(R.string.default_web_client_id) )
.requestEmail()
.build();

mGoogleApiClient = new GoogleApiClient.Builder( mActivity )
.addApi( Auth.GOOGLE_SIGN_IN_API, gso )
.build();
 */
class RxGoogleSignIn(
    val context: Context,
    val googleSignInOptions: GoogleSignInOptions = gso
) {
    val googleApiClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(context, googleSignInOptions)
    }

    @NonNull
    fun getSignInIntent(): Intent {
        return googleApiClient.getSignInIntent()
    }

    @NonNull
    fun revokeAccess(): Completable {
        return googleApiClient.revokeAccess().let { task ->
            RxTask.completable(task)
        }
    }

    @NonNull
    fun signOut(): Completable {
        return googleApiClient.signOut().let { task ->
            RxTask.completable(task)
        }
    }

    @NonNull
    fun silentSignIn(): Single<GoogleSignInAccount> {
        return googleApiClient.silentSignIn().let { task ->
            RxTask.single(task)
        }
    }

    fun handleSignInResult(data: Intent?): Single<Account> {
        GoogleSignIn.getSignedInAccountFromIntent(data).let {
            return Single.create<GoogleSignInAccount> { emitter ->
                it.addOnCompleteListener(RxTask.listener(emitter))
            }.map { t -> t.account }
        }
    }

    fun getIdToken(data: Intent): String {
        GoogleSignIn.getSignedInAccountFromIntent(data).let {
            it.getResult(ApiException::class.java)!!.let { account ->
                return account.idToken!!
            }
        }
    }

    companion object {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
}