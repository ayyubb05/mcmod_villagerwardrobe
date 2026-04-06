package com.pak.villagerwardrobe.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;

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
}
