package com.suppergerrie2.soundreloader;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(SoundReloader.MOD_ID)
public class SoundReloader
{
    public static final String MOD_ID = "ssoundreloader";

    public SoundReloader() {
        DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> ClientEvents::new);
    }
}
