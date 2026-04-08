package com.pak.villagerwardrobe.item.custom;

import com.pak.villagerwardrobe.entity.ModEntities;
import com.pak.villagerwardrobe.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.List;
import java.util.Map;


public class HangerItem extends Item {
  private static final Map<Block, Block> HANGER_MAP =
      Map.of(
          Blocks.GRASS_BLOCK, Blocks.STONE,
          Blocks.STONE, Blocks.GRASS_BLOCK
      );

  public HangerItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

    if(HANGER_MAP.containsKey(clickedBlock)){
      if(!level.isClientSide()){
        level.setBlockAndUpdate(context.getClickedPos(), HANGER_MAP.get(clickedBlock).defaultBlockState());

        context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), context.getPlayer(),
            item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

        level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);
      }
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
    Level level = player.level();
    if(!level.isClientSide() && interactionTarget instanceof Villager villager){
      if (stack.getItem() == ModItems.HANGER.get()) {
        // Capture full NBT for potential reversion
        Vec3 villagerPos = villager.position();
        CompoundTag villagerNBT = new CompoundTag();
        villager.saveWithoutId(villagerNBT);
        // Remove the original villager
        villager.remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);

        // Create a new villager (or your custom NPC)
        Villager newVillager = ModEntities.TRAINER.get().create(level); // replace with CustomNPC entity type if desired
        if (newVillager != null) {
          // Apply captured NBT
          newVillager.load(villagerNBT);
          // Set position and rotation
          newVillager.setPos(villagerPos.x, villagerPos.y, villagerPos.z);
//          newVillager.setYRot(villager.getYRot());
//          newVillager.setXRot(villager.getXRot());
          // Add the new entity to the world
          level.addFreshEntity(newVillager);
        }
        // Notify the player using NeoForge API
        player.displayClientMessage(Component.literal("Trainer has been modified."), true);

        // Optionally shrink the item
//        stack.shrink(1);
      }
    }
    return InteractionResult.SUCCESS;
  }
}
