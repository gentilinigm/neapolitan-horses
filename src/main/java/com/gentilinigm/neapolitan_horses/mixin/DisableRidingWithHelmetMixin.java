package com.gentilinigm.neapolitan_horses.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;


@Mixin(AbstractHorse.class)
public class DisableRidingWithHelmetMixin{

   @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;setEating(Z)V", shift = At.Shift.AFTER), method = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;doPlayerRide(Lnet/minecraft/world/entity/player/Player;)V", cancellable = true)
    protected void hasHelmet(Player player, CallbackInfo ci) {
        if (!player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {

            Component message = getHelmetMessage();
            if(message != null)
                player.displayClientMessage(message, true);

            ci.cancel();
        }
    }

    @Nullable
    private Component getHelmetMessage()
    {
        String messageKey = "neapolitan_horses.tooltip.horse.remove_helmet";

        if(!Language.getInstance().has(messageKey))
            return null;

        return new TranslatableComponent(messageKey).withStyle(ChatFormatting.BOLD, ChatFormatting.RED);
    }
}
