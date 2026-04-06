package com.pak.villagerwardrobe.item;

import com.pak.villagerwardrobe.VillagerWardrobe;
import com.pak.villagerwardrobe.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VillagerWardrobe.MOD_ID);

  public static final Supplier<CreativeModeTab> WARDROBE_ITEMS_TAB = CREATIVE_MODE_TAB.register("wardrobe_items_tab",
      () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WARDROBE_BLOCK.get()))
          .title(Component.translatable("creativetab.villagerwardrobe.wardrobe_items"))
          .displayItems((itemDisplayParameters, output) -> {
            output.accept(ModBlocks.WARDROBE_BLOCK);
            output.accept((ModItems.WOODEN_HANGER));
            output.accept((ModItems.METAL_HANGER));
            output.accept((ModItems.HANGER));
          }).build());


  public static void register (IEventBus eventBus) {
    CREATIVE_MODE_TAB.register(eventBus);
  }
}
