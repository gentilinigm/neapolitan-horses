package com.gentilinigm.neapolitan_horses.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public class DisableOtherRidersJumpMixin {

    @Redirect(method = "Lnet/minecraft/client/player/LocalPlayer;aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isRidingJumpable()Z"))
    private boolean preventJump(LocalPlayer lp)
    {
        if(((LocalPlayer) (Object) this).getVehicle() instanceof AbstractHorse)
            return ((LocalPlayer) (Object) this).isRidingJumpable() && ((LocalPlayer) (Object) this).getVehicle().getPassengers().indexOf(((LocalPlayer) (Object) this)) < 1;

        return ((LocalPlayer) (Object) this).isRidingJumpable();
    }

}
