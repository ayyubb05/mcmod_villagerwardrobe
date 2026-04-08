package com.pak.villagerwardrobe.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class TrainerEntity extends Villager {
  public static final AnimationState idleAnimationState = new AnimationState();
//  private int idleAnimationTimeout = 0;
  private static final EntityDataAccessor<Integer> TYPE =
      SynchedEntityData.defineId(TrainerEntity.class, EntityDataSerializers.INT);

  public TrainerEntity(EntityType<? extends Villager> entityType, Level level) {
    super(entityType, level);
    if (!level.isClientSide) {
      this.setTrainerType(this.random.nextInt(5));
    }
  }

  public static AttributeSupplier.Builder createAttributes(){
    return Villager.createAttributes()
        .add(Attributes.MAX_HEALTH, 10D)
        .add(Attributes.MOVEMENT_SPEED, 0.25D)
        .add(Attributes.FOLLOW_RANGE, 24D);
  }

  @Override
  protected void registerGoals() {
    this.goalSelector.addGoal(0, (new FloatGoal(this)));
    this.goalSelector.addGoal(1, (new WaterAvoidingRandomStrollGoal(this, 1f)));
    this.goalSelector.addGoal(0, (new LookAtPlayerGoal(this, Player.class, 6f)));
    this.goalSelector.addGoal(0, (new RandomLookAroundGoal(this)));

    super.registerGoals();
  }

  @Override
  public void addAdditionalSaveData(CompoundTag tag) {
    super.addAdditionalSaveData(tag);
    tag.putInt("TrainerType", this.getTrainerType());
  }

  @Override
  public void readAdditionalSaveData(CompoundTag tag) {
    super.readAdditionalSaveData(tag);

    if (tag.contains("TrainerType")) {
      this.setTrainerType(tag.getInt("TrainerType"));
    }
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
    builder.define(TYPE, 0); // default
  }

  public void setTrainerType(int type) {
    this.entityData.set(TYPE, type);
  }

  public int getTrainerType() {
    return this.entityData.get(TYPE);
  }

  @Override
  public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
    int randomType = this.random.nextInt(5);
    this.setTrainerType(randomType);
    return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
  }
}
