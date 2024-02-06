package com.gentilinigm.neapolitan_horses.render;

import com.gentilinigm.neapolitan_horses.config.ClientConfig;
import com.gentilinigm.neapolitan_horses.entity.NeapolitanHorse;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class NeapolitanHorseRenderer extends AbstractHorseRenderer<NeapolitanHorse, HorseModel<NeapolitanHorse>> {
    public NeapolitanHorseRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel<>(context.bakeLayer(ModelLayers.HORSE)), 1.1F);

        if(ClientConfig.RENDER_ARMOR.get())
            this.addLayer(new NeapolitanHorseArmorLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(NeapolitanHorse horse) {
        return new ResourceLocation("neapolitan_horses:textures/entity/horse_naples.png");
    }
}
