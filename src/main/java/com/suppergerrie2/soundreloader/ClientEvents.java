package com.suppergerrie2.soundreloader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;


public class ClientEvents {
    public static final KeyBinding reloadSoundsKey = new KeyBinding(SoundReloader.MOD_ID + ".key.reload_sounds",
                                                                    KeyConflictContext.UNIVERSAL,
                                                                    KeyModifier.ALT,
                                                                    InputMappings.Type.KEYSYM,
                                                                    GLFW.GLFW_KEY_G,
                                                                    "key.categories." + SoundReloader.MOD_ID);
    private static final Logger LOGGER = LogManager.getLogger();
    boolean wasPressed = false;

    public ClientEvents() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetupEvent);
        MinecraftForge.EVENT_BUS.addListener(this::keyEvent);
    }

    public void clientSetupEvent(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(reloadSoundsKey);
    }

    public void keyEvent(InputEvent.KeyInputEvent event) {
        if (reloadSoundsKey.isPressed()) {
            if (!wasPressed) {
                LOGGER.info("Reloading sound engine...");
                Minecraft.getInstance().getSoundHandler().sndManager.reload();
                ClientPlayerEntity player = Minecraft.getInstance().player;
                if (player != null) {
                    player.sendMessage(new StringTextComponent("[SoundReloader] Sound engine reloaded")
                                               .setStyle(new Style().setColor(TextFormatting.YELLOW)));
                }
            }

            wasPressed = true;
        } else {
            wasPressed = false;
        }
    }

}
