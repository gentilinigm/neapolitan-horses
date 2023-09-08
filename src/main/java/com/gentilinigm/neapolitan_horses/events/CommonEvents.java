package com.gentilinigm.neapolitan_horses.events;

import com.gentilinigm.neapolitan_horses.registry.EntityTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonEvents {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        if(event.getName().equals(new ResourceLocation("minecraft:plains"))){
            event.getSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypeRegistry.NEAPOLITAN_HORSE.get(), 1, 1, 1));
        }
    }

}
