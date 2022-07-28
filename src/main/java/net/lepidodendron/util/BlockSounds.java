package net.lepidodendron.util;

import net.lepidodendron.LepidodendronMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@SuppressWarnings("WeakerAccess")
@GameRegistry.ObjectHolder(LepidodendronMod.MODID)
public final class BlockSounds {

    @GameRegistry.ObjectHolder("wet_crunch_plants")
    public static final SoundEvent WET_CRUNCH_PLANTS = createSoundEvent("wet_crunch_plants");

    @GameRegistry.ObjectHolder("dry_crunch_plants")
    public static final SoundEvent DRY_CRUNCH_PLANTS = createSoundEvent("dry_crunch_plants");


    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(LepidodendronMod.MODID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }
}
