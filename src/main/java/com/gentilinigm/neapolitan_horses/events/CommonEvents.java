package com.gentilinigm.neapolitan_horses.events;

import com.gentilinigm.neapolitan_horses.config.CommonConfig;
import com.gentilinigm.neapolitan_horses.registry.EntityTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class CommonEvents {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(BiomeLoadingEvent event) {

        List<? extends String> biomeConfig = CommonConfig.NEAPOLITAN_HORSE_CONFIG.BIOME_WHITELIST.get();

        if(biomeConfig.stream().anyMatch(b -> event.getName().equals(new ResourceLocation(b)))){

            int spawnRateConfig = CommonConfig.NEAPOLITAN_HORSE_CONFIG.SPAWN_RATE.get();
            int minGroupSizeConfig = CommonConfig.NEAPOLITAN_HORSE_CONFIG.MIN_GROUP_SIZE.get();
            int maxGroupSizeConfig = CommonConfig.NEAPOLITAN_HORSE_CONFIG.MAX_GROUP_SIZE.get();

            event.getSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypeRegistry.NEAPOLITAN_HORSE.get(), spawnRateConfig, minGroupSizeConfig, maxGroupSizeConfig));
        }
    }

}
