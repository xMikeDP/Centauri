package com.github.xmikedp.centauri.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

object ConfigManager {
    private val gson: Gson by lazy { GsonBuilder().setPrettyPrinting().create() }

    private lateinit var configFile: File

    var config: CentauriConfig = CentauriConfig()
        private set

    fun init(configDir: File) {
        val modConfigDir = File(configDir, "centauri")
        if (!modConfigDir.exists()) {
            modConfigDir.mkdirs()
        }
        configFile = File(modConfigDir, "config.json")
        loadConfig()
    }

    fun loadConfig() {
        if(!::configFile.isInitialized) {
            println("[Centauri] Config file not initialized")
            return
        }

        if(!configFile.exists()) {
            println("[Centauri] Config file not found, creating default")
            saveConfig()
            return
        }

        try {
            FileReader(configFile).use { reader ->
                config = gson.fromJson(reader, CentauriConfig::class.java) ?: CentauriConfig()
                println("[Centauri] Config file loaded successfully")
            }
        } catch (e: IOException) {
            println("[Centauri] Error loading config: ${e.message}")
            e.printStackTrace()
        } catch (e: JsonSyntaxException) {
            println("[Centauri] Error parsing config JSON: ${e.message}")
            e.printStackTrace()
            saveConfig()
        }
    }

    fun saveConfig() {
        if(!::configFile.isInitialized) {
            println("[Centauri] ConfigManager not initialized! Cannot save.")
            return
        }

        try {
            FileWriter(configFile).use { writer ->
                gson.toJson(config, writer)
                println("[Centauri] Config saved successfully")
            }
        } catch (e: IOException) {
            println("[Centauri] Error saving config: ${e.message}")
            e.printStackTrace()
        }
    }
}