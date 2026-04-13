package com.pak.villagerwardrobe.entity.custom;

import com.pak.villagerwardrobe.screen.custom.Outfit;
import com.pak.villagerwardrobe.util.WardrobeOutfitLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
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

import java.util.List;

public class TrainerEntity extends Villager {
  private static final EntityDataAccessor<String> OUTFIT_ID =
      SynchedEntityData.defineId(TrainerEntity.class, EntityDataSerializers.STRING);
  private static final EntityDataAccessor<String> OUTFIT_TEXTURE =
      SynchedEntityData.defineId(TrainerEntity.class, EntityDataSerializers.STRING);

  public TrainerEntity(EntityType<? extends Villager> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.Builder createAttributes() {
    return Villager.createAttributes()
        .add(Attributes.MAX_HEALTH, 10D)
        .add(Attributes.MOVEMENT_SPEED, 0.25D)
        .add(Attributes.FOLLOW_RANGE, 24D);
  }

  @Override
  protected void registerGoals() {
    this.goalSelector.addGoal(0, new FloatGoal(this));
    this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1f));
    this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 6f));
    this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
    super.registerGoals();
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
    builder.define(OUTFIT_ID, "");
    builder.define(OUTFIT_TEXTURE, "");
  }

  // Set from an Outfit object — called at spawn time from the item
  public void setOutfit(Outfit outfit) {
    this.entityData.set(OUTFIT_ID, outfit.id());
    this.entityData.set(OUTFIT_TEXTURE, outfit.texture());
  }

  // Set from just an id string — called during load
  public void setOutfitId(String id) {
    this.entityData.set(OUTFIT_ID, id);
    // Also restore the texture by looking up the outfit from the loader
    WardrobeOutfitLoader.INSTANCE.getOutfitById(id)
        .ifPresent(outfit -> this.entityData.set(OUTFIT_TEXTURE, outfit.texture()));
  }

  public String getOutfitId() {
    return this.entityData.get(OUTFIT_ID);
  }

  // Called by the renderer to get the texture path
  public String getOutfitTexture() {
    return this.entityData.get(OUTFIT_TEXTURE);
  }

  @Override
  public void addAdditionalSaveData(CompoundTag tag) {
    super.addAdditionalSaveData(tag);
    tag.putString("OutfitId", this.getOutfitId());
  }

  @Override
  public void readAdditionalSaveData(CompoundTag tag) {
    super.readAdditionalSaveData(tag);
    if (tag.contains("OutfitId")) {
      this.setOutfitId(tag.getString("OutfitId"));
    }
  }

  @Override
  public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                                MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
    // Pick a random outfit from the loader as the default
    List<Outfit> outfits = WardrobeOutfitLoader.INSTANCE.getAllOutfits();
    if (!outfits.isEmpty()) {
      Outfit random = outfits.get(this.random.nextInt(outfits.size()));
      this.setOutfit(random);
    }
    return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
  }
}