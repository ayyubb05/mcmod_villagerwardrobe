//package com.pak.villagerwardrobe.screen.custom;
//
//import com.pak.villagerwardrobe.VillagerWardrobe;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.components.ObjectSelectionList;
//import net.minecraft.resources.ResourceLocation;
//
//import java.util.List;
//
//public class TrainerList extends ObjectSelectionList<TrainerEntry> {
//
//  private static final List<ResourceLocation> TEXTURES = List.of(
//      ResourceLocation.fromNamespaceAndPath(VillagerWardrobe.MOD_ID, "textures/entity/trainer/mcskins_pokemon_trainer1.png"),
//      ResourceLocation.fromNamespaceAndPath(VillagerWardrobe.MOD_ID, "textures/entity/trainer/mcskins_pokemon_trainer2.png")
//  );
//
//  public TrainerList(Minecraft mc, int width, int height, int top, int bottom) {
//    super(mc, width, height, top, bottom); // 5 args is correct in 1.21.1+
//
//    for (int i = 0; i < TEXTURES.size(); i++) {
//      this.addEntry(new TrainerEntry(TEXTURES.get(i), i));
//    }
//  }
//}
