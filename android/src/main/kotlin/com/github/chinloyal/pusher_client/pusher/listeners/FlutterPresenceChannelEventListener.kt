package com.github.chinloyal.pusher_client.pusher.listeners

import com.github.chinloyal.pusher_client.core.utils.Constants
import com.github.chinloyal.pusher_client.pusher.PusherService
import com.google.gson.JsonObject
import com.pusher.client.channel.PresenceChannelEventListener
import com.pusher.client.channel.PusherEvent
import com.pusher.client.channel.User
import java.lang.Exception

class FlutterPresenceChannelEventListener: FlutterBaseChannelEventListener(), PresenceChannelEventListener {
    companion object {
        val instance = FlutterPresenceChannelEventListener()
    }

    override fun onUsersInformationReceived(channelName: String, users: MutableSet<User>) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("event", Constants.SUBSCRIPTION_SUCCEEDED.value)
        jsonObject.addProperty("channel", channelName)
        jsonObject.addProperty("user_id", -1)
        jsonObject.addProperty("data",users.toString() )
        this.onEvent(PusherEvent(jsonObject))
    }

    override fun userUnsubscribed(channelName: String, user: User) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("event", Constants.MEMBER_REMOVED.value)
        jsonObject.addProperty("channel", channelName)
        jsonObject.addProperty("user_id", user.id)
        jsonObject.addProperty("data","" )
        this.onEvent(PusherEvent(jsonObject))
    }

    override fun userSubscribed(channelName: String, user: User) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("event", Constants.MEMBER_ADDED.value)
        jsonObject.addProperty("channel", channelName)
        jsonObject.addProperty("user_id", user.id)
        jsonObject.addProperty("data","" )
        this.onEvent(PusherEvent(jsonObject))
    }

    override fun onAuthenticationFailure(message: String, e: Exception) {
        PusherService.errorLog(message)
        if(PusherService.enableLogging) e.printStackTrace()
    }

    override fun onSubscriptionSucceeded(channelName: String) {
        PusherService.debugLog("[PRESENCE] Subscribed: $channelName")
        val jsonObject = JsonObject()
        jsonObject.addProperty("event", Constants.SUBSCRIPTION_SUCCEEDED.value)
        jsonObject.addProperty("channel", channelName)
        jsonObject.addProperty("user_id", -1)
        jsonObject.addProperty("data","" )
        this.onEvent(PusherEvent(jsonObject))
    }
}