package com.kottodat.gsfirebase.fcm

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.kottodat.gsfirebase.rx.RxTask
import io.reactivex.Single

class RxFirebaseMessage {

    fun getFcmToken(): Single<String> =
        FirebaseInstanceId.getInstance().instanceId.let { task ->
            return Single.create<InstanceIdResult> {
                task.addOnCompleteListener(RxTask.listener(it))
            }.map { t -> t.token }
        }
}