package com.pak.villagerwardrobe.block.entity;

import com.pak.villagerwardrobe.VillagerWardrobe;
import com.pak.villagerwardrobe.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
      DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, VillagerWardrobe.MOD_ID);

  public static final Supplier<BlockEntityType<WardrobeBlockEntity>> WARDROBE_BE =
      BLOCK_ENTITIES.register("wardrobe_be", () -> BlockEntityType.Builder.of(
          WardrobeBlockEntity::new, ModBlocks.WARDROBE_BLOCK.get()).build(null));

  public static void register(IEventBus eventBus){
    BLOCK_ENTITIES.register(eventBus);
  }
}
