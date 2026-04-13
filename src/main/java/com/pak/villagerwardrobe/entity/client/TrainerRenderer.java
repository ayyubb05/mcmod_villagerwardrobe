package com.pak.villagerwardrobe.entity.client;

import com.pak.villagerwardrobe.VillagerWardrobe;
import com.pak.villagerwardrobe.entity.custom.TrainerEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TrainerRenderer extends MobRenderer<TrainerEntity, PlayerModel<TrainerEntity>> {
  public TrainerRenderer (EntityRendererProvider.Context context){
    super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5f);
  }
  @Override
  public ResourceLocation getTextureLocation(TrainerEntity entity) {
    String texture = entity.getOutfitTexture();
    if (texture == null || texture.isEmpty()) {
      return ResourceLocation.fromNamespaceAndPath(VillagerWardrobe.MOD_ID,
          "textures/entity/trainer/mcskins_pokemon_trainer5.png"); // fallback
    }
    return ResourceLocation.parse(texture);
  }

}
