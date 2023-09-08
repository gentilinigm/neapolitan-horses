package com.gentilinigm.neapolitan_horses.events;

import com.gentilinigm.neapolitan_horses.render.NeapolitanHorseRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.gentilinigm.neapolitan_horses.registry.EntityTypeRegistry.NEAPOLITAN_HORSE;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents
    {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerEntityRenderer(NEAPOLITAN_HORSE.get(), NeapolitanHorseRenderer::new);
        }
    }
}
