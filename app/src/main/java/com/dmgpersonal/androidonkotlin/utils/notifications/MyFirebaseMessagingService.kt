package com.dmgpersonal.androidonkotlin.utils.notifications

import android.util.Log
import com.dmgpersonal.androidonkotlin.utils.NOTIFICATION_KEY_MESSAGE
import com.dmgpersonal.androidonkotlin.utils.NOTIFICATION_KEY_TITLE
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("@@@", token)
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        val title = data[NOTIFICATION_KEY_TITLE]
        val body = data[NOTIFICATION_KEY_MESSAGE]
        if(!title.isNullOrEmpty() && !body.isNullOrEmpty()) {
            pushNotification(title, body)
        }

        super.onMessageReceived(message)
    }
}

fun showToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.d("@@@", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }
        val token = task.result
        Log.d("@@@", token)
    })
}