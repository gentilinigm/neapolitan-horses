package com.gentilinigm.neapolitan_horses.entity;

import com.gentilinigm.neapolitan_horses.registry.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SoundType;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class NeapolitanHorse extends AbstractHorse {

    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");

    public static boolean checkNeapolitanHorseSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
        return p_27579_.getBlockState(p_27581_.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(p_27579_, p_27581_);
    }


    public NeapolitanHorse(EntityType<? extends AbstractHorse> horse, Level level) {
        super(horse, level);
    }

    @Override
    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            super.updateContainerEquipment();
            this.setArmorEquipment(this.inventory.getItem(1));
            this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    private void setArmorEquipment(ItemStack itemStack) {
        this.setArmor(itemStack);
        if (!this.level.isClientSide) {
            this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
            if (this.isArmor(itemStack)) {
                int i = ((HorseArmorItem)itemStack.getItem()).getProtection();
                if (i != 0) {
                    this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
                }
            }
        }

    }

    public ItemStack getArmor() {
        return this.getItemBySlot(EquipmentSlot.CHEST);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (!this.inventory.getItem(1).isEmpty()) {
            compoundTag.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
        }

    }
    private void setArmor(ItemStack p_30733_) {
        this.setItemSlot(EquipmentSlot.CHEST, p_30733_);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
    }

    @Override
    public void containerChanged(Container p_30696_) {
        ItemStack itemstack = this.getArmor();
        super.containerChanged(p_30696_);
        ItemStack itemstack1 = this.getArmor();
        if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
            this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
        }

    }

    @Override
    protected void playGallopSound(SoundType p_30709_) {
        super.playGallopSound(p_30709_);
        if (this.random.nextInt(10) == 0) {
            this.playSound(SoundEvents.HORSE_BREATHE, p_30709_.getVolume() * 0.6F, p_30709_.getPitch());
        }

        ItemStack stack = this.inventory.getItem(1);
        if (isArmor(stack)) stack.onHorseArmorTick(level, this);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.HORSE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.HORSE_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getEatingSound() {
        return SoundEvents.HORSE_EAT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_30720_) {
        super.getHurtSound(p_30720_);
        return SoundEvents.HORSE_HURT;
    }

    @Override
    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.HORSE_ANGRY;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (!this.isBaby()) {
            if (this.isTamed() && player.isSecondaryUseActive()) {
                this.openInventory(player);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            if (this.isVehicle()) {
                return super.mobInteract(player, interactionHand);
            }
        }

        if (!itemstack.isEmpty()) {
            if (this.isFood(itemstack)) {
                return this.fedFood(player, itemstack);
            }

            InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, interactionHand);
            if (interactionresult.consumesAction()) {
                return interactionresult;
            }

            if (!this.isTamed()) {
                this.makeMad();
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            boolean flag = !this.isBaby() && !this.isSaddled() && itemstack.is(Items.SADDLE);
            if (this.isArmor(itemstack) || flag) {
                this.openInventory(player);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }

        if (this.isBaby()) {
            return super.mobInteract(player, interactionHand);
        } else {
            this.doPlayerRide(player);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
    }

    protected boolean otherCanParent(AbstractHorse horse) {
        return !horse.isVehicle() && !horse.isPassenger() && horse.isTamed() && !horse.isBaby() && horse.getHealth() >= horse.getMaxHealth() && horse.isInLove();
    }

    @Override
    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        } else if (!(animal instanceof Donkey) && !(animal instanceof Horse)) {
            return false;
        } else {
            return this.canParent() && otherCanParent((AbstractHorse) animal);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        AbstractHorse abstracthorse;

        if(ageableMob instanceof Donkey)
            abstracthorse = EntityType.MULE.create(serverLevel);
        else
            abstracthorse = EntityTypeRegistry.NEAPOLITAN_HORSE.get().create(serverLevel);
        return abstracthorse;
    }

    @Override
    public boolean canWearArmor() {
        return true;
    }

    @Override
    public boolean isArmor(ItemStack itemStack) {
        return itemStack.getItem() instanceof HorseArmorItem;
    }

    @Override
    protected void randomizeAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
        this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());

        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getMaxSpeed());

    }

    protected double getMaxSpeed(){
        return ((double)0.45F + /*this.random.nextDouble()*/ 1.0D * 0.3D + /*this.random.nextDouble()*/ 1.0D * 0.3D + /*this.random.nextDouble()*/ 1.0D * 0.3D) * 0.25D;
    }
}
