package com.pak.villagerwardrobe.component;

import com.pak.villagerwardrobe.VillagerWardrobe;
import com.pak.villagerwardrobe.component.custom.OutfitComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {

  public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
      DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, VillagerWardrobe.MOD_ID);

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<OutfitComponent>> OUTFIT =
      DATA_COMPONENTS.register("outfit", () ->
          DataComponentType.<OutfitComponent>builder()
              .persistent(OutfitComponent.CODEC)
              .networkSynchronized(OutfitComponent.STREAM_CODEC)
              .build()
      );

  public static void register(IEventBus eventBus) {
    DATA_COMPONENTS.register(eventBus);
  }
}