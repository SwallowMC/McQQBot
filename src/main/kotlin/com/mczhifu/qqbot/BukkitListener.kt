package com.mczhifu.qqbot

import com.mczhifu.mczf.enumerate.EventType
import com.mczhifu.mczf.event.BukkitMczfEvent
import com.mczhifu.mczf.json.JSONObject
import com.mczhifu.mczf.message.Message
import com.mczhifu.mczf.pack.PlayerPack
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class BukkitListener : Listener{
    @EventHandler
    public fun onKrypton(event: BukkitMczfEvent) {
        if (event.eventType == EventType.SHIP_EVENT){
            var player = event.getObject(0) as PlayerPack
            var json = event.getObject(1) as JSONObject

            var msg = Message.set(configManager.allConfig["config"]?.getString("qq-group.pay-notify.message"))
                .parse("%player%", player.name)
                .parse("%paymoney%", json.getDouble("paymoney").toString())
                .parse("%info%", json.getString("info").toString())
                .parsePlaceholder(player)
                .toString()

            GlobalScope.async {
                if (mczfBot.bot.isOnline) mczfBot.sendMessageToGroup(806288057L,msg)
            }
        }
    }
}