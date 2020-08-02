package com.davidchu.andmycodeisended

import com.google.firebase.iid.FirebaseInstanceId

object TokenUtil {
    fun callWithNewToken(callback: (String?) -> Unit) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (!task.isSuccessful || task.result == null)
                    callback(null)
                else
                    callback(task.result!!.token)
            }
    }
}