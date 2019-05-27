package com.kottodat.gsfirebase.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kottodat.gsfirebase.rx.RxTask
import io.reactivex.Completable
import io.reactivex.Single

open class RxFirebaseAuth {
    companion object {

        fun signInWithCredential(firebaseAuth: FirebaseAuth, credential: AuthCredential): Single<FirebaseUser> =
            firebaseAuth.signInWithCredential(credential).let {
                return login(it)
            }

        fun signInWithCustomToken(firebaseAuth: FirebaseAuth, token: String): Single<FirebaseUser> =
            firebaseAuth.signInWithCustomToken(token).let {
                return login(it)
            }

        fun signInWithEmailAndPassword(firebaseAuth: FirebaseAuth, email: String, pass: String): Single<FirebaseUser> =
            firebaseAuth.signInWithEmailAndPassword(email, pass).let {
                return login(it)
            }

        fun signOut(firebaseAuth: FirebaseAuth) = firebaseAuth.signOut()

        inline fun login(task: Task<AuthResult>): Single<FirebaseUser> {
            return Single.create<AuthResult> { emitter ->
                task.addOnCompleteListener(RxTask.listener(emitter))
            }.map { t -> t.user }
        }
    }
}