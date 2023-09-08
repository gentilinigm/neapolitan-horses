package com.gentilinigm.neapolitan_horses.registry;

import com.gentilinigm.neapolitan_horses.NeapolitanHorses;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

//@Mod.EventBusSubscriber(modid = NeapolitanHorses.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ITEMS, NeapolitanHorses.MODID);

    public static final RegistryObject<ForgeSpawnEggItem> NEAPOLITAN_HORSE_SPAWN_EGG = DEF_REG.register("neapolitan_horse_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.NEAPOLITAN_HORSE, 0X189acf, 0X003c7d, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus){
        DEF_REG.register(eventBus);
    }
}
