package com.davidchu.andmycodeisended

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import java.util.function.Predicate

object TokenUtil {
    fun callWithNewToken(callback: (String?) -> Unit) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful || task.result == null)
                    callback(null)
                else
                    callback(task.result!!.token)
            })
    }
}