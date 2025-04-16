package com.github.xmikedp.centauri.utils

import com.github.xmikedp.centauri.Centauri
import com.github.xmikedp.centauri.Centauri.Companion.PREFIX
import net.minecraft.util.ChatComponentText

object ChatUtils {
    fun sendClientSideMessage(message: String) {
        if(Centauri.mc.thePlayer != null) {
            Centauri.mc.thePlayer.addChatMessage(ChatComponentText("$PREFIX $message"))
        }
    }

    fun sendChatMessage(message: String) {
        if(Centauri.mc.thePlayer != null) {
            Centauri.mc.thePlayer.sendChatMessage(message)
        }
    }

    fun sendAllChatMessage(message: String) {
        if(Centauri.mc.thePlayer != null) {
            Centauri.mc.thePlayer.sendChatMessage("/ac $message")
        }
    }

    fun sendPartyChatMessage(message: String) {
        if(Centauri.mc.thePlayer != null) {
            Centauri.mc.thePlayer.sendChatMessage("/pc $message")
        }
    }
}