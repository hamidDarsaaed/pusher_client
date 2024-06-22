package com.github.chinloyal.pusher_client.pusher.listeners

import com.github.chinloyal.pusher_client.core.utils.Constants
import com.github.chinloyal.pusher_client.pusher.PusherService
import com.github.chinloyal.pusher_client.pusher.PusherService.Companion.enableLogging
import com.github.chinloyal.pusher_client.pusher.PusherService.Companion.errorLog
import com.google.gson.JsonObject
import com.pusher.client.channel.PrivateChannelEventListener
import com.pusher.client.channel.PusherEvent



import java.lang.Exception

class FlutterPrivateChannelEventListener: FlutterBaseChannelEventListener(), PrivateChannelEventListener {
    companion object {
        val instance = FlutterPrivateChannelEventListener()
    }

    override fun onAuthenticationFailure(message: String, e: Exception) {
        errorLog(message)
        if(enableLogging) e.printStackTrace()
    }

    override fun onSubscriptionSucceeded(channelName: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("event", Constants.SUBSCRIPTION_SUCCEEDED.value)
        jsonObject.addProperty("channel", channelName)
        jsonObject.addProperty("user_id", -1)
        jsonObject.addProperty("data","" )
        this.onEvent(PusherEvent(jsonObject))
        PusherService.debugLog("[PRIVATE] Subscribed: $channelName")
    }
}