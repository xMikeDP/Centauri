package com.github.xmikedp.centauri;

import com.github.xmikedp.centauri.config.CentauriConfig
import com.github.xmikedp.centauri.config.ConfigManager
import com.github.xmikedp.centauri.config.features.FeatureA
import com.github.xmikedp.centauri.ui.CentauriGui
import com.github.xmikedp.centauri.utils.ChatUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import org.lwjgl.input.Keyboard
import java.io.File

@Mod(
    modid = Centauri.MOD_ID,
    name = Centauri.MOD_NAME,
    version = Centauri.MOD_VERSION,
    clientSideOnly = true
)

class Centauri {
    var lastLongUpdate: Long = 0
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        val directory = File(event.modConfigurationDirectory, "centauri")
        directory.mkdirs()

        val configDir = event.suggestedConfigurationFile.parentFile
        ConfigManager.init(configDir)

        val cch = ClientCommandHandler.instance

        // REGISTER COMMANDS
        //cch.registerCommand(SettingsCommand())
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        println("t")
        //config.init()
        // REGISTER CLASSES/ETC
        val mcBus = MinecraftForge.EVENT_BUS
        mcBus.register(this)

        //mcBus.register(EntityESP)
        //mcBus.register(FarmingKeys)
        //mcBus.register(BlockESP)
        mcBus.register(FeatureA)

        keyBinds.forEach(ClientRegistry::registerKeyBinding)
    }

//    @SubscribeEvent
//    fun onTick(event: TickEvent.ClientTickEvent) {
//        val currTime = System.currentTimeMillis()
//        if (currTime - lastLongUpdate > 1000) {
//            lastLongUpdate = currTime
//            println("t")
//        }
//    }

    @SubscribeEvent
    fun onKey(event: InputEvent.KeyInputEvent) {
        if(keyBinds[0].isPressed) {
            ChatUtils.sendClientSideMessage("Test")
            mc.displayGuiScreen(CentauriGui())
        }
    }

    companion object {
        const val MOD_ID = "centauri"
        const val MOD_NAME = "Centauri"
        const val MOD_VERSION = "0.0.1"
        const val PREFIX = "§8[§7Centauri§8]§r"

        val mc: Minecraft = Minecraft.getMinecraft()
        var config = ConfigManager.config
        var display: GuiScreen? = null

        val keyBinds = arrayOf(
            KeyBinding("Open GUI", Keyboard.KEY_RCONTROL, "Centauri")
        )
//        val IO = object : CoroutineScope {
//            override val coroutineContext = Dispatchers.IO + SupervisorJob() + CoroutineName("centauri IO")
//        }
    }
}

