package com.gentilinigm.neapolitan_horses.mixin;

import com.gentilinigm.neapolitan_horses.config.CommonConfig;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class HorseAllowMoreRidersMixin extends Animal implements ContainerListener, PlayerRideableJumping, Saddleable {


    private static final int MAX_PASSENGERS = CommonConfig.GENERAL_CONFIG.MAXIMUM_PASSENGERS.get();

    @Shadow
    private float standAnimO;

    protected HorseAllowMoreRidersMixin(EntityType<? extends AbstractHorse> horse, Level level) {
        super(horse, level);
    }

    @Inject(at = @At(value = "TAIL"), method = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;positionRider(Lnet/minecraft/world/entity/Entity;)V")
    private void fixRiderPosition(Entity entity, CallbackInfo ci) {
        if(this.getPassengers().size() < 2)
            return;

        /*if (this.standAnimO == 0.0F)
            return;*/

        float offset = this.getRidersOffset(this.getPassengers().indexOf(entity));

        Vec3 vec3 = new Vec3(offset, 0.0, 0.0).yRot(-1 * (this.getYRot() * (float) Math.PI / 180.0f) - (float) Math.PI / 2.0f);

        double xPos = this.getX() + vec3.x;
        double yPos = this.getY() + (this.isRemoved() ? 0.01d : this.getPassengersRidingOffset() + entity.getMyRidingOffset() + (0.15d * this.standAnimO));
        double zPos = this.getZ() + vec3.z;

        entity.setPos(xPos, yPos, zPos);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.isBaby() && this.isVehicle() && this.canAddPassenger(player)) {
            player.startRiding(this);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return this.getPassengers().size() < MAX_PASSENGERS;
    }

    private float getRidersOffset(int index){
        if(this.getPassengers().size() < 2)
            return 0;

        if(index < 0 || index > MAX_PASSENGERS)
            return 0;

        float firstOffset = 0.2f;
        float interval = 0.8f;

        return firstOffset - ((interval / (this.getPassengers().size() - 1)) * index);
    }
}
