package com.kottodat.gsfirebase.rx

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.annotations.NonNull
import javax.annotation.CheckReturnValue

class RxTask {
    companion object {
        @NonNull
        fun <R> completable(task: Task<R>): Completable {
            return Completable.create { emitter ->
                task.addOnCompleteListener(listener(emitter))
            }
        }

        @NonNull
        fun <R> single(task: Task<R>): Single<R> {
            return Single.create<R> { emitter ->
                task.addOnCompleteListener(listener(emitter))
            }
        }

        @NonNull
        @CheckReturnValue
        fun <R> listener(@NonNull emit: SingleEmitter<R>): OnCompleteListener<R> {
            return OnCompleteListener { task ->
                try {
                    if (!emit.isDisposed) {
                        if (!task.isSuccessful) {
                            emit.onError(task.exception!!)
                        } else {
                            emit.onSuccess(task.result!!)
                        }
                    }
                } catch (e: Exception) {
                    emit.onError(e)
                }
            }
        }

        @NonNull
        @CheckReturnValue
        fun <R> listener(@NonNull emit: CompletableEmitter): OnCompleteListener<R> {
            return OnCompleteListener { task ->
                try {
                    if (!emit.isDisposed) {
                        if (!task.isSuccessful) {
                            emit.onError(task.exception!!)
                        } else {
                            emit.onComplete()
                        }
                    }
                } catch (e: Exception) {
                    emit.onError(e)
                }
            }
        }
    }
}