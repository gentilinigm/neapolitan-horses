package com.gentilinigm.neapolitan_horses.render;

import com.gentilinigm.neapolitan_horses.entity.NeapolitanHorse;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NeapolitanHorseArmorLayer extends RenderLayer<NeapolitanHorse, HorseModel<NeapolitanHorse>> {
    private final HorseModel<NeapolitanHorse> model;

    public NeapolitanHorseArmorLayer(RenderLayerParent<NeapolitanHorse, HorseModel<NeapolitanHorse>> p_174496_, EntityModelSet p_174497_) {
        super(p_174496_);
        this.model = new HorseModel<>(p_174497_.bakeLayer(ModelLayers.HORSE_ARMOR));
    }

    public void render(PoseStack p_117032_, MultiBufferSource p_117033_, int p_117034_, NeapolitanHorse p_117035_, float p_117036_, float p_117037_, float p_117038_, float p_117039_, float p_117040_, float p_117041_) {
        ItemStack itemstack = p_117035_.getArmor();
        if (itemstack.getItem() instanceof HorseArmorItem horsearmoritem) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(p_117035_, p_117036_, p_117037_, p_117038_);
            this.model.setupAnim(p_117035_, p_117036_, p_117037_, p_117039_, p_117040_, p_117041_);
            float f;
            float f1;
            float f2;
            if (horsearmoritem instanceof DyeableHorseArmorItem) {
                int i = ((DyeableHorseArmorItem)horsearmoritem).getColor(itemstack);
                f = (float)(i >> 16 & 255) / 255.0F;
                f1 = (float)(i >> 8 & 255) / 255.0F;
                f2 = (float)(i & 255) / 255.0F;
            } else {
                f = 1.0F;
                f1 = 1.0F;
                f2 = 1.0F;
            }

            VertexConsumer vertexconsumer = p_117033_.getBuffer(RenderType.entityCutoutNoCull(horsearmoritem.getTexture()));
            this.model.renderToBuffer(p_117032_, vertexconsumer, p_117034_, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
        }
    }



}
