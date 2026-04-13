//package com.pak.villagerwardrobe.network;
//
//
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.minecraft.resources.ResourceLocation;
//
//public record SelectTrainerPacket(int index) implements CustomPacketPayload {
//  public static final ResourceLocation ID =
//      new ResourceLocation("villagerwardrobe", "select_trainer");
//
//  public static final StreamCodec<FriendlyByteBuf, SelectTrainerPacket> CODEC =
//      StreamCodec.of(
//          (buf, pkt) -> buf.writeInt(pkt.index),
//          buf -> new SelectTrainerPacket(buf.readInt())
//      );
//
//  @Override
//  public ResourceLocation id() {
//    return ID;
//  }
//
//  @Override
//  public Type<? extends CustomPacketPayload> type() {
//    return null;
//  }
//}
