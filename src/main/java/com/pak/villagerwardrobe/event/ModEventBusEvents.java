package com.pak.villagerwardrobe.event;

import com.pak.villagerwardrobe.VillagerWardrobe;
import com.pak.villagerwardrobe.entity.ModEntities;
import com.pak.villagerwardrobe.entity.custom.TrainerEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = VillagerWardrobe.MOD_ID)
public class ModEventBusEvents {
  @SubscribeEvent
  public static void registerAttributes(EntityAttributeCreationEvent event){
    event.put(ModEntities.TRAINER.get(), TrainerEntity.createAttributes().build());
  }
}
