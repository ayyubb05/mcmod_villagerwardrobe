package com.pak.villagerwardrobe.item;

import com.pak.villagerwardrobe.VillagerWardrobe;
import com.pak.villagerwardrobe.item.custom.HangerItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems  {
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(VillagerWardrobe.MOD_ID);

  public static final DeferredItem<Item> WOODEN_HANGER = ITEMS.register("wooden_hanger",
      () -> new Item(new Item.Properties()));
  public static final DeferredItem<Item> METAL_HANGER = ITEMS.register("metal_hanger",
      () -> new Item(new Item.Properties()));

  public static final DeferredItem<Item> HANGER = ITEMS.register("hanger",
      () -> new HangerItem(new Item.Properties().durability(32)));

  public static void register(IEventBus eventBus) {
      ITEMS.register(eventBus);
  }
}
