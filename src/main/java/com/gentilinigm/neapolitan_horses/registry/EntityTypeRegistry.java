package com.gentilinigm.neapolitan_horses.registry;

import com.gentilinigm.neapolitan_horses.NeapolitanHorses;
import com.gentilinigm.neapolitan_horses.entity.NeapolitanHorse;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NeapolitanHorses.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.ENTITIES, NeapolitanHorses.MODID);

    public static final RegistryObject<EntityType<NeapolitanHorse>> NEAPOLITAN_HORSE = DEF_REG.register("neapolitan_horse", () -> EntityType.Builder.of(NeapolitanHorse::new, MobCategory.CREATURE).build("neapolitan_horse"));

    @SubscribeEvent
    public static void initializeAttributes(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(NEAPOLITAN_HORSE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, NeapolitanHorse::checkNeapolitanHorseSpawnRules);

        event.put(NEAPOLITAN_HORSE.get(), NeapolitanHorse.createBaseHorseAttributes().build());
    }

    public static void register(IEventBus eventBus){
        DEF_REG.register(eventBus);
    }

}
