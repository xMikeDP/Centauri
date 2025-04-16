package com.github.xmikedp.centauri.config.features

import com.github.xmikedp.centauri.config.ConfigManager.config
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object FeatureA {
    private var lastLongUpdate: Long = 0
    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if(event.phase != TickEvent.Phase.END) return
        if(!config.isFeatureAEnabled) return

        val currTime = System.currentTimeMillis()
        if (currTime - lastLongUpdate > 1000) {
            lastLongUpdate = currTime
            println("t")
        }
    }
}