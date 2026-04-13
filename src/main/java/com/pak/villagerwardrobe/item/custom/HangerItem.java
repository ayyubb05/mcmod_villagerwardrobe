package com.pak.villagerwardrobe.item.custom;

import com.pak.villagerwardrobe.component.ModDataComponents;
import com.pak.villagerwardrobe.component.custom.OutfitComponent;
import com.pak.villagerwardrobe.entity.ModEntities;
import com.pak.villagerwardrobe.entity.custom.TrainerEntity;
import com.pak.villagerwardrobe.item.ModItems;
import com.pak.villagerwardrobe.util.WardrobeOutfitLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.phys.Vec3;

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
        Vec3 villagerPos = villager.position();

        // Remove the original villager
        villager.remove(Entity.RemovalReason.DISCARDED);

        TrainerEntity newTrainer = ModEntities.TRAINER.get().create(level);
        if (newTrainer != null) {
          // Set position
          newTrainer.setPos(villagerPos.x, villagerPos.y, villagerPos.z);

          // Add to world (triggers finalizeSpawn which sets a random type)
          level.addFreshEntity(newTrainer);

          // Override random type with outfit from item if present
          OutfitComponent outfitComp = stack.get(ModDataComponents.OUTFIT.get());
          if (outfitComp != null) {
            WardrobeOutfitLoader.INSTANCE.getOutfitById(outfitComp.outfitName())
                .ifPresent(newTrainer::setOutfit);
            player.displayClientMessage(
                Component.literal("Trainer spawned with outfit: " + outfitComp.outfitName()), true);
          } else {
            player.displayClientMessage(
                Component.literal("No outfit found on item, using random."), true);
          }
        }
        // Optionally shrink the item
        //  stack.shrink(1);
      }
    }
    return InteractionResult.SUCCESS;
  }
}
