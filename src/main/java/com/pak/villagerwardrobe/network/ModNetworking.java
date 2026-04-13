//package com.pak.villagerwardrobe.network;
//
//
//import com.pak.villagerwardrobe.screen.custom.WardrobeMenu;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.neoforged.neoforge.network.PacketDistributor;
//import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
//
//public class ModNetworking {
//
//  public static void register(RegisterPayloadHandlersEvent event) {
//
//    event.registrar("1")
//        .playToServer(
//            SelectTrainerPacket.ID,
//            SelectTrainerPacket.CODEC,
//            (packet, context) -> {
//              var player = context.player();
//              if (player.containerMenu instanceof WardrobeMenu menu) {
//                menu.handleTrainerSelection(packet.index(), player);
//              }
//            }
//        );
//  }
//
//  public static void sendToServer(CustomPacketPayload payload) {
//    PacketDistributor.SERVER.noArg().send(payload);
//  }
//}
