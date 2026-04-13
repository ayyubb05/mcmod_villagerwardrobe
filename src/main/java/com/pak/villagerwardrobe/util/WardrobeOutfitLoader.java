package com.pak.villagerwardrobe.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.pak.villagerwardrobe.screen.custom.Outfit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WardrobeOutfitLoader extends SimpleJsonResourceReloadListener {

  public static final WardrobeOutfitLoader INSTANCE = new WardrobeOutfitLoader();

  // This is where your loaded options will live
  private List<Outfit> outfits = List.of();

  public WardrobeOutfitLoader() {
    super(new Gson(), "outfits"); // looks in data/<modid>/outfits/
  }

  @Override
  protected void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager manager, ProfilerFiller profiler) {
    List<Outfit> loaded = new ArrayList<>();
    for (Map.Entry<ResourceLocation, JsonElement> entry : objects.entrySet()) {
      JsonArray array = entry.getValue().getAsJsonObject().getAsJsonArray("outfits");
      for (JsonElement el : array) {
        loaded.add(Outfit.fromJson(el.getAsJsonObject()));
      }
    }
    this.outfits = loaded;
  }

  public List<Outfit> getAllOutfits() {
    return outfits;
  }

  // Convenience lookup by id
  public Optional<Outfit> getOutfitById(String id) {
    return outfits.stream().filter(o -> o.id().equals(id)).findFirst();
  }

  // For the screen — just display names
  public List<String> getDisplayNames() {
    return outfits.stream().map(Outfit::displayName).toList();
  }
}

