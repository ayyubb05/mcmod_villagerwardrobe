package com.pak.villagerwardrobe.entity;

import com.pak.villagerwardrobe.VillagerWardrobe;
import com.pak.villagerwardrobe.entity.custom.TrainerEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
  public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
      DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, VillagerWardrobe.MOD_ID);

  public static final Supplier<EntityType<TrainerEntity>> TRAINER =
      ENTITY_TYPES.register("trainer",
          () -> EntityType.Builder.of(TrainerEntity::new, MobCategory.CREATURE)
              .sized(.75f, 2f).build("trainer"));


  public static void register(IEventBus eventBus){
    ENTITY_TYPES.register(eventBus);
  }
}
