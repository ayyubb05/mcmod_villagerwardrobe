package com.pak.villagerwardrobe.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record OutfitComponent(String outfitName) {

  public static final Codec<OutfitComponent> CODEC = RecordCodecBuilder.create(instance ->
      instance.group(
          Codec.STRING.fieldOf("outfit_name").forGetter(OutfitComponent::outfitName)
      ).apply(instance, OutfitComponent::new)
  );

  public static final StreamCodec<ByteBuf, OutfitComponent> STREAM_CODEC =
      StreamCodec.composite(
          ByteBufCodecs.STRING_UTF8,
          OutfitComponent::outfitName,
          OutfitComponent::new
      );
}