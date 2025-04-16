package com.github.xmikedp.centauri.ui

import com.github.xmikedp.centauri.config.CentauriConfig
import com.github.xmikedp.centauri.config.ConfigManager
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiButton // Still needed for potentially the Done button, or remove if fully custom
import net.minecraft.util.EnumChatFormatting
import net.minecraft.client.renderer.GlStateManager // For more advanced rendering if needed
import net.minecraft.util.ResourceLocation // For textures
import org.lwjgl.opengl.GL11

class CentauriGui : GuiScreen() {

    private val config: CentauriConfig = ConfigManager.config
    private val useTextures: Boolean = true

    // --- Define regions for custom elements ---
    // We'll store them as simple data classes or maps
    data class CustomElement(
        val id: Int,
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int,
        val label: String,
        var isHovered: Boolean = false // State for hover effect
    )

    private val customElements = mutableListOf<CustomElement>()
    private lateinit var elementFeatureA: CustomElement
    private lateinit var elementFeatureB: CustomElement
    private lateinit var elementOther: CustomElement

    // --- Textures (Optional) ---
    // Example: val MY_TEXTURE = ResourceLocation("yourmodid", "textures/gui/my_custom_gui.png")
    private val BUTTONTEXTURE = ResourceLocation("centauri", "textures/gui/widgets.png")
    //private val BUTTONTEXTURE = ResourceLocation("minecraft", "textures/gui/widgets.png")

    // Helper function to format button text ON/OFF (can be reused)
    private fun getFormattedBoolean(featureName: String, value: Boolean): String {
        val status = if (value) "${EnumChatFormatting.GREEN}ON" else "${EnumChatFormatting.RED}OFF"
        return "$featureName: $status"
    }

    // Helper function to check mouse collision
    private fun isMouseOver(mouseX: Int, mouseY: Int, element: CustomElement): Boolean {
        return mouseX >= element.x && mouseX < element.x + element.width &&
                mouseY >= element.y && mouseY < element.y + element.height
    }

    override fun initGui() {
        super.initGui()
        this.buttonList.clear() // Clear default buttons
        customElements.clear() // Clear our custom elements

        val elementWidth = 150
        val elementHeight = 20
        val horizontalCenter = this.width / 2
        val verticalStart = this.height / 2 - 40
        val spacing = 5

        var currentY = verticalStart

        // Define Feature A Element
        elementFeatureA = CustomElement(
            0,
            horizontalCenter - elementWidth / 2, currentY,
            elementWidth, elementHeight,
            "Feature A" // Base label
        )
        customElements.add(elementFeatureA)
        currentY += elementHeight + spacing

        // Define Feature B Element
        elementFeatureB = CustomElement(
            1,
            horizontalCenter - elementWidth / 2, currentY,
            elementWidth, elementHeight,
            "Feature B"
        )
        customElements.add(elementFeatureB)
        currentY += elementHeight + spacing

        // Define Other Setting Element
        elementOther = CustomElement(
            2,
            horizontalCenter - elementWidth / 2, currentY,
            elementWidth, elementHeight,
            "Other Setting"
        )
        customElements.add(elementOther)
        currentY += elementHeight + spacing


        // You can still use GuiButton for standard things if you want, like "Done"
        this.buttonList.add(GuiButton(
            100,
            horizontalCenter - 100,
            this.height - 28,
            200, 20, // Standard height
            "Done"
        ))
    }

    // Handle clicks for custom elements
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton) // Handle clicks for GuiButtons (like Done)

        if (mouseButton == 0) { // Left click
            for (element in customElements) {
                if (isMouseOver(mouseX, mouseY, element)) {
                    // Click detected on this element, perform action based on ID
                    when (element.id) {
                        0 -> { // Feature A
                            config.isFeatureAEnabled = !config.isFeatureAEnabled
                            ConfigManager.saveConfig()
                        }
                        1 -> { // Feature B
                            config.isFeatureBToggled = !config.isFeatureBToggled
                            ConfigManager.saveConfig()
                        }
                        2 -> { // Other Setting
                            config.someOtherSetting = !config.someOtherSetting
                            ConfigManager.saveConfig()
                        }
                    }
                    // Optional: Play a sound
                    // this.mc.soundHandler.playSound(PositionedSoundRecord.create(SoundEvents.UI_BUTTON_CLICK, 1.0F))
                    break // Stop checking once an element is clicked
                }
            }
        }
    }

//    fun drawRoundedRect(x: Int, y: Int, width: Int, height: Int, radius: Int, color: Int) {
//        // Ensure radius is not too large
//        val effectiveRadius = Math.min(Math.min(radius, width / 2), height / 2)
//
//        // Central rectangle
//        drawRect(x + effectiveRadius, y + effectiveRadius, x + width - effectiveRadius, y + height - effectiveRadius, color)
//        // Top rectangle (excluding corners)
//        drawRect(x + effectiveRadius, y, x + width - effectiveRadius, y + effectiveRadius, color)
//        // Bottom rectangle (excluding corners)
//        drawRect(x + effectiveRadius, y + height - effectiveRadius, x + width - effectiveRadius, y + height, color)
//        // Left rectangle (including corners vertically)
//        drawRect(x, y + effectiveRadius, x + effectiveRadius, y + height - effectiveRadius, color)
//        // Right rectangle (including corners vertically)
//        drawRect(x + width - effectiveRadius, y + effectiveRadius, x + width, y + height - effectiveRadius, color)
//    }
//
//
//    // Inside drawScreen:
//    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
//        // ... (background, title, hover checks) ...
//
//        for (element in customElements) {
//            val backgroundColor = if (element.isHovered) 0xFF5555FF else 0xFF000088
//            val cornerRadius = 5 // Adjust as needed
//
//            // --- Use the helper function ---
//            drawRoundedRect(element.x, element.y, element.width, element.height, cornerRadius, backgroundColor.toInt())
//
//            // Draw text (same as before)
//            val labelText = getLabelForElement(element)
//            val textColor = 0xFFFFFFFF
//            this.drawCenteredString(
//                this.fontRendererObj,
//                labelText,
//                element.x + element.width / 2,
//                element.y + (element.height - 8) / 2,
//                textColor.toInt()
//            )
//        }
//
//        // ... (super.drawScreen) ...
//    }
//
//    // Make sure you have the getLabelForElement helper from the previous example too
//    private fun getLabelForElement(element: CustomElement): String {
//        return when (element.id) {
//            0 -> getFormattedBoolean(element.label, config.isFeatureAEnabled)
//            1 -> getFormattedBoolean(element.label, config.isFeatureBToggled)
//            2 -> getFormattedBoolean(element.label, config.someOtherSetting)
//            else -> element.label
//        }
//    }
//
//    // Keep the getFormattedBoolean helper too
//    private fun getFormattedBoolean(featureName: String, value: Boolean): String {
//        val status = if (value) "${EnumChatFormatting.GREEN}ON" else "${EnumChatFormatting.RED}OFF"
//        return "$featureName: $status"
//    }

    // Draw everything manually
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.drawDefaultBackground() // Background


        println("Binding texture: $BUTTONTEXTURE")
        this.mc.textureManager.bindTexture(BUTTONTEXTURE)

        //GlStateManager.enableBlend()

        // Title
        this.drawCenteredString(this.fontRendererObj, "Centauri", this.width / 2, 20, 0xFFFFFF)

        // Update hover state for custom elements
        customElements.forEach { it.isHovered = isMouseOver(mouseX, mouseY, it) }

        // --- Draw Custom Elements ---
        for (element in customElements) {
            // Determine appearance based on state (hovered, etc.)
            val backgroundColor = if (element.isHovered) 0xFF5555FF else 0xFF000088 // Example: Blue, lighter blue on hover
            val textColor = 0xFFFFFFFF // White text

            // Draw background rectangle
            //drawRect(element.x, element.y, element.x + element.width, element.y + element.height, backgroundColor.toInt())

            // Draw text (get current value for label)
            val labelText = when (element.id) {
                0 -> getFormattedBoolean(element.label, config.isFeatureAEnabled)
                1 -> getFormattedBoolean(element.label, config.isFeatureBToggled)
                2 -> getFormattedBoolean(element.label, config.someOtherSetting)
                else -> element.label // Fallback
            }
//            this.drawCenteredString(
//                this.fontRendererObj,
//                labelText,
//                element.x + element.width / 2,
//                element.y + (element.height - 8) / 2, // Center text vertically
//                textColor.toInt()
//            )

            // --- Advanced Drawing (Optional: Textures) ---
             if (useTextures) {
                this.mc.textureManager.bindTexture(BUTTONTEXTURE)
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F) // Reset color tint
                val u = if (element.isHovered) 0 else 150 // Example: Different texture part on hover
                val v = 0
                this.drawTexturedModalRect(element.x, element.y, u, v, element.width, element.height)
                // Draw text over the texture...
                 this.drawCenteredString(
                     this.fontRendererObj,
                     labelText,
                     element.x + element.width / 2,
                     element.y + (element.height - 8) / 2, // Center text vertically
                     textColor.toInt()
                 )
             }
        }


        // Draw GuiButtons (like Done) last, so they appear on top if overlapping
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    // Handle standard button actions (like the Done button)
    override fun actionPerformed(button: GuiButton) {
        // No need to call super if we handle everything or cleared buttonList initially for custom ones
        if (button.id == 100) { // Done button
            this.mc.displayGuiScreen(null)
            // ConfigManager.saveConfig() // Optional save on close
        }
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun onGuiClosed() {
        super.onGuiClosed()
        // ConfigManager.saveConfig() // Optional save on close via ESC
    }
}